package cl.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    
    @NotEmpty(message = "username no puede estar vacio")
    private String username;
    
    @NotEmpty(message = "Password no puede estar vacio")
    @Size(min = 6, message = "Password no puede ser menor a 6 caracteres")
    private String password;
    
    @NotEmpty(message = "Role no puede estar vacio")
    private String role;
    
}