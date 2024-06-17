package com.restfulnplc.nplcrestful.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.LoginDTO;
import com.restfulnplc.nplcrestful.dto.AccessDTO;
import com.restfulnplc.nplcrestful.model.Divisi;
import com.restfulnplc.nplcrestful.model.Login;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Role;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.model.Tipegame;
import com.restfulnplc.nplcrestful.repository.LoginRepository;
import com.restfulnplc.nplcrestful.util.PasswordHasherMatcher;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordHasherMatcher passwordMaker;

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private BoothgamesService boothgamesService;

    @Autowired
    private TeamService teamService;

    public Optional<Login> LoginPanitia(LoginDTO loginDTO) {
        Optional<Panitia> panitiaOptional = panitiaService.findPanitiaByUsername(loginDTO.getUsername());
        if (panitiaOptional.isPresent()) {
            Panitia panitia = panitiaOptional.get();
            if (passwordMaker.matchPassword(loginDTO.getPassword(), panitia.getPassword())) {
                Optional<Login> sessionActive = getLoginSessionByUser(panitia.getIdPanitia());
                if (sessionActive.isPresent()) {
                    deleteSession(sessionActive.get().getToken());
                }
                Role role = Role.PANITIA;
                if(boothgamesService.getBoothgameByPanitia(panitia.getIdPanitia()).isPresent()) {
                    role = (boothgamesService.getBoothgameByPanitia(panitia.getIdPanitia()).get().getTipegame().equals(Tipegame.SINGLE)) ? Role.LOSINGLE : Role.LODUEL;
                }
                if(panitia.getIsAdmin()) {
                    role = Role.ADMIN;
                }
                if(panitia.getDivisi().equals(Divisi.KETUAACARA)) {
                    role = Role.KETUA;
                }
                Login session = new Login(panitia.getIdPanitia(), passwordMaker.hashPassword(panitia.getIdPanitia()),
                        role);
                loginRepository.save(session);
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    public Optional<Login> LoginPlayer(LoginDTO loginDTO) {
        Optional<Team> teamOptional = teamService.findTeamByUsername(loginDTO.getUsername());
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            if (passwordMaker.matchPassword(loginDTO.getPassword(), team.getPassUsr())) {
                Optional<Login> sessionActive = getLoginSessionByUser(team.getIdTeam());
                if (sessionActive.isPresent()) {
                    deleteSession(sessionActive.get().getToken());
                }
                Login session = new Login(team.getIdTeam(), passwordMaker.hashPassword(team.getIdTeam()), Role.PLAYERS);
                loginRepository.save(session);
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    public boolean checkSessionAlive(String token) {
        if (token != null) {
            if(!checkUserExist(token)) {
                deleteSession(token);
            }
            return loginRepository.findById(token).isPresent();
        }
        return false;
    }

    public boolean checkUserExist(String token) {
        Login session = getLoginSession(token);
        return (panitiaService.getPanitiaById(session.getIdUser()).isPresent() && teamService.getTeamById(session.getIdUser()).isPresent());
    }

    public boolean checkSessionSelf(String token, String idUser) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                return loginRepository.findById(token).get().getIdUser().equals(idUser);
            }
        }
        return false;
    }

    public boolean checkSessionPanitia(String token) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                Login session = loginRepository.findById(token).get();
                if (session.getRole().equals(Role.PANITIA)) {
                    return panitiaService.checkPanitia(session.getIdUser());
                }
            }
        }
        return false;
    }

    public boolean checkSessionAdmin(String token) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                Login session = loginRepository.findById(token).get();
                if (session.getRole().equals(Role.PANITIA)) {
                    return panitiaService.checkAdmin(session.getIdUser());
                }
            }
        }
        return false;
    }

    public boolean checkSessionTeam(String token) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                Login session = loginRepository.findById(token).get();
                if (session.getRole().equals(Role.PLAYERS)) {
                    return teamService.checkTeam(session.getIdUser());
                }
            }
        }
        return false;
    }

    public boolean checkSessionKetua(String token) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                Login session = loginRepository.findById(token).get();
                if (session.getRole().equals(Role.PANITIA)) {
                    return panitiaService.checkKetua(session.getIdUser());
                }
            }
        }
        return false;
    }

    public boolean checkSessionLOSingle(String token) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                Login session = loginRepository.findById(token).get();
                if(panitiaService.checkPanitia(session.getIdUser())) return session.getRole().equals(Role.LOSINGLE);
            }
        }
        return false;
    }

    public boolean checkSessionLODuel(String token) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                Login session = loginRepository.findById(token).get();
                if(panitiaService.checkPanitia(session.getIdUser())) session.getRole().equals(Role.LODUEL);
            }
        }
        return false;
    }

    public boolean checkSessionLOGame(String token) {
        if (token != null) {
            if (loginRepository.findById(token).isPresent()) {
                Login session = loginRepository.findById(token).get();
                if(panitiaService.checkPanitia(session.getIdUser())) return (session.getRole().equals(Role.LODUEL) || session.getRole().equals(Role.LOSINGLE));
            }
        }
        return false;
    }

    public Login getLoginSession(String token) {
        return loginRepository.findById(token).get();
    }

    public Optional<Login> getLoginSessionByUser(String userId) {
        for (Login session : loginRepository.findAll()) {
            if (session.getIdUser().equals(userId))
                return Optional.of(session);
        }
        return Optional.empty();
    }

    public boolean userIsLoggedIn(String userId) {
        if (getLoginSessionByUser(userId).isPresent())
            return true;
        return false;
    }

    public void deleteSession(String token) {
        loginRepository.deleteById(token);
    }

    public AccessDTO getAccessDetails(String sessionToken) {
        Login sessionActive = getLoginSession(sessionToken);
        boolean isAdmin = checkSessionAdmin(sessionToken);
        String idUser = sessionActive.getIdUser();
        AccessDTO accessDetails = new AccessDTO(
                idUser,
                sessionActive.getRole().toString(),
                isAdmin,
                isAdmin ? (panitiaService.getPanitiaById(idUser).get().getDivisi().toString()) : null);
        return accessDetails;
    }

    public void reset()
    {
        loginRepository.deleteAll();
    }
}
