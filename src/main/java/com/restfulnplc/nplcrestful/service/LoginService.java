package com.restfulnplc.nplcrestful.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.LoginDTO;
import com.restfulnplc.nplcrestful.model.Login;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Team;
import com.restfulnplc.nplcrestful.model.Role;
import com.restfulnplc.nplcrestful.repository.LoginRepository;
import com.restfulnplc.nplcrestful.util.PasswordHasherMatcher;

@Service
public class LoginService {
    private final String encodedAdminPassword = "$2a$10$YOgnsr/9C7DazPD5tph5Ou5taF7FizE.0jwnXRy8NX7o1MI297QgG";

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordHasherMatcher passwordMaker;

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private TeamService teamService;

    public Optional<Login> LoginPanitia(LoginDTO loginDTO)
    {
        Optional<Panitia> panitiaOptional = panitiaService.findPanitiaByUsername(loginDTO.getUsername());
        if (panitiaOptional.isPresent()) {
            Panitia panitia = panitiaOptional.get();
            if(passwordMaker.matchPassword(loginDTO.getPassword(), panitia.getPassword())){
                Optional<Login> sessionActive = getLoginSessionByUser(panitia.getIdPanitia());
                if(sessionActive.isPresent()){
                    deleteSession(sessionActive.get().getToken());
                }
                Login session = new Login(panitia.getIdPanitia(), passwordMaker.hashPassword(panitia.getIdPanitia()), Role.PANITIA);
                loginRepository.save(session);
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    public Optional<Login> LoginPlayer(LoginDTO loginDTO)
    {
        Optional<Team> teamOptional = teamService.findTeamByUsername(loginDTO.getUsername());
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            if(passwordMaker.matchPassword(loginDTO.getPassword(), team.getPassUsr())){
                Optional<Login> sessionActive = getLoginSessionByUser(team.getIdTeam());
                if(sessionActive.isPresent()){
                    deleteSession(sessionActive.get().getToken());
                }
                Login session = new Login(team.getIdTeam(), passwordMaker.hashPassword(team.getIdTeam()), Role.PLAYERS);
                loginRepository.save(session);
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    public boolean checkAdminPassword(String password)
    {
        return passwordMaker.matchPassword(password, encodedAdminPassword);
    }

    public boolean checkSessionAlive(String token)
    {
        return loginRepository.findById(token).isPresent();
    }

    public boolean checkSessionAdmin(String token)
    {
        if(loginRepository.findById(token).isPresent()) {
            Login session = loginRepository.findById(token).get();
            if(session.getRole().equals(Role.PANITIA)) {
                return panitiaService.checkPanitia(session.getIdUser());
            }
        }
        return false;
    }

    public boolean checkSessionTeam(String token)
    {
        if(loginRepository.findById(token).isPresent()) {
            Login session = loginRepository.findById(token).get();
            if(session.getRole().equals(Role.PLAYERS)) {
                return teamService.checkTeam(session.getIdUser());
            }
        }
        return false;
    }

    public Login getLoginSession(String token)
    {
        return loginRepository.findById(token).get();
    }

    public Optional<Login> getLoginSessionByUser(String userId)
    {
        for(Login session : loginRepository.findAll()){
            if(session.getIdUser().equals(userId)) return Optional.of(session);
        }
        return Optional.empty();
    }

    public boolean userIsLoggedIn(String userId)
    {
        if(getLoginSessionByUser(userId).isPresent()) return true;
        return false;
    }

    public void deleteSession(String token)
    {
        loginRepository.deleteById(token);
    }
}
