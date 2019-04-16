package cl.ms.serviceTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import cl.ms.dto.LoginRequestDTO;
import cl.ms.dto.PhonesDTO;
import cl.ms.dto.UsuarioDTO;
import cl.ms.exception.ErrorCredencialesException;
import cl.ms.exception.ErrorNegocioException;
import cl.ms.model.UsuarioModel;
import cl.ms.repository.UsuarioRepository;
import cl.ms.security.JwtGenerator;
import cl.ms.service.UsuServiceImpl;

@RunWith(SpringRunner.class)
public class ServiceTest {

	@Spy
	DozerBeanMapper dozerBeanMapper;
	@InjectMocks
	private UsuServiceImpl service = new UsuServiceImpl();
	
	@Mock
	private UsuarioRepository usuarioRepository;
	@Mock
	private JwtGenerator jwtGenerator;
	@Test
	public void addRegistroOk() {
		
		UsuarioModel usuModelExist = new UsuarioModel();
		usuModelExist.setCreated(LocalDateTime.now());
		usuModelExist.setEmail("test@test.com");
		usuModelExist.setLast_login(LocalDateTime.now());
		usuModelExist.setModified(LocalDateTime.now());
		usuModelExist.setName("nombre");
		usuModelExist.setPassword("pass");
		usuModelExist.setUsuCod(UUID.randomUUID());
		Mockito.when(usuarioRepository.findByEmail(Mockito.any(String.class))).thenReturn(null).thenReturn(usuModelExist);
		
		UsuarioDTO usu = UsuarioDTO.builder().build();
		usu.setEmail("test@test.com");
		usu.setName("nombre");
		usu.setPassword("pass");
		List<PhonesDTO> listPhone = new ArrayList<PhonesDTO>();
		PhonesDTO phone = PhonesDTO.builder().build();
		phone.setCitycode("1");
		phone.setContrycode("21");
		phone.setNumber("312314");
		listPhone.add(phone);
		usu.setPhones(listPhone);
		service.addRegistro(usu);
		
	}
	
	@Test
	public void addRegistroExistente() {
		
		findUsuario();
		
		UsuarioDTO usu = UsuarioDTO.builder().build();
		usu.setEmail("test@test.com");
		usu.setName("nombre");
		usu.setPassword("pass");
		List<PhonesDTO> listPhone = new ArrayList<PhonesDTO>();
		PhonesDTO phone = PhonesDTO.builder().build();
		phone.setCitycode("1");
		phone.setContrycode("21");
		phone.setNumber("312314");
		listPhone.add(phone);
		usu.setPhones(listPhone);
		try {
			service.addRegistro(usu);
		} catch (ErrorNegocioException e) {
			Assert.assertEquals("El correo ya registrado" , e.getMensaje());
		}
	}
	@Test
	public void loginOk() {
		
		findUsuario();
		
		LoginRequestDTO loginReq = LoginRequestDTO.builder().build();
		loginReq.setContrasena("pass");
		loginReq.setCorreo("test@test.com");
		service.login(loginReq);
	}
	
	@Test
	public void loginUsuarioNoExiste() {
		
		LoginRequestDTO loginReq = LoginRequestDTO.builder().build();
		loginReq.setContrasena("pass");
		loginReq.setCorreo("test@test.com");
		try {
			service.login(loginReq);

		} catch (ErrorNegocioException e) {
			Assert.assertEquals("Usuario o contraseña inválidos" , e.getMensaje());
		}
	}
	@Test
	public void loginUsuarioPassIncorrecto() {
		
		findUsuario();
		
		LoginRequestDTO loginReq = LoginRequestDTO.builder().build();
		loginReq.setContrasena("pass1");
		loginReq.setCorreo("test@test.com");
		try {
			service.login(loginReq);

		} catch (ErrorCredencialesException e) {
			Assert.assertEquals(" Usuario o contraseña inválidos " , e.getMensaje());
		}
	}

	private void findUsuario() {
		UsuarioModel usuModelExist = new UsuarioModel();
		usuModelExist.setCreated(LocalDateTime.now());
		usuModelExist.setEmail("test@test.com");
		usuModelExist.setLast_login(LocalDateTime.now());
		usuModelExist.setModified(LocalDateTime.now());
		usuModelExist.setName("nombre");
		usuModelExist.setPassword("pass");
		usuModelExist.setUsuCod(UUID.randomUUID());
		Mockito.when(usuarioRepository.findByEmail(Mockito.any(String.class))).thenReturn(usuModelExist);
	}


}
