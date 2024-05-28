package com.restfulnplc.nplcrestful.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulnplc.nplcrestful.dto.LoginDTO;
import com.restfulnplc.nplcrestful.dto.PanitiaDTO;
import com.restfulnplc.nplcrestful.model.Login;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Role;
import com.restfulnplc.nplcrestful.repository.LoginRepository;
import com.restfulnplc.nplcrestful.repository.PanitiaRepository;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.util.PasswordHasherMatcher;

@Service
public class LoginService {
    private final String encodedAdminPassword = "$2a$10$YOgnsr/9C7DazPD5tph5Ou5taF7FizE.0jwnXRy8NX7o1MI297QgG";

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PanitiaRepository panitiaRepository;

    @Autowired
    private PasswordHasherMatcher passwordMaker;

    @Autowired
    private PanitiaService panitiaService;

    public Optional<Login> LoginPanitia(LoginDTO loginDTO)
    {
        Optional<Panitia> panitiaOptional = panitiaService.findPanitiaByUsername(loginDTO.getUsername());
        if (panitiaOptional.isPresent()) {
            Panitia panitia = panitiaOptional.get();
            if(passwordMaker.matchPassword(loginDTO.getPassword(), panitia.getPassword())){
                Login session = new Login(panitia.getIdPanitia(), passwordMaker.hashPassword(panitia.getIdPanitia()), Role.PANITIA);
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
}
