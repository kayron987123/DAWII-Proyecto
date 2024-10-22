package pe.edu.cibertec.frontend_rt_fc.controller;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.frontend_rt_fc.client.LoginClient;
import pe.edu.cibertec.frontend_rt_fc.dto.LoginRequestDTO;
import pe.edu.cibertec.frontend_rt_fc.dto.LoginResponseDTO;
import pe.edu.cibertec.frontend_rt_fc.viewmodel.LoginModel;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginClient loginClient;

    @Autowired
    RestTemplate restTemplateAutenticacion;

    @GetMapping("/inicio")
    public String inicio(Model model){

        LoginModel loginModel = new LoginModel("00", "");
        model.addAttribute("loginModel", loginModel);

        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam String codigo, @RequestParam String password, Model model) {
        if (codigo == null || codigo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            LoginModel loginModel = new LoginModel("01", "Error: Debe completar correctamente sus credenciales");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }

        try {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(codigo, password);
            ResponseEntity<LoginResponseDTO> response = loginClient.login(loginRequestDTO);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                LoginModel loginModel = new LoginModel("00", "Te has autenticado correctamente");
                model.addAttribute("loginModel", loginModel);
                model.addAttribute("userData", response.getBody());
                return "detalle";
            } else {
                LoginModel loginModel = new LoginModel("02", "Error: autenticacion fallida");
                model.addAttribute("loginModel", loginModel);
                return "inicio";
            }
        } catch (FeignException e) {
            logger.error("Error during login", e);
            LoginModel loginModel = new LoginModel("99", "Error en el servidor: " + e.getMessage());
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }
    }

    @GetMapping("/get-integrantes")
    public ResponseEntity<List<LoginResponseDTO>> obtenerIntegrantes(Model model){
        ResponseEntity<List<LoginResponseDTO>> response = restTemplateAutenticacion.exchange("/get-integrantes", HttpMethod.GET, null, new ParameterizedTypeReference<List<LoginResponseDTO>>() {});
        return ResponseEntity.ok(response.getBody());
    }


}
