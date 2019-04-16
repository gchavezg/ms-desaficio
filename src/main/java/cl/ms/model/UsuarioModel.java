	package cl.ms.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * Model de la solicitud de inversion
 * @author everis
 */
@Entity
@Table(name = "USUARIO", uniqueConstraints = @UniqueConstraint(columnNames={"usu_cod"}))
public class UsuarioModel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private UUID usuCod;
	
	private String name;
	
	private String email;
	
	private String password;
	
	@Temporal(TemporalType.DATE)
	private LocalDateTime created;
	
	@Temporal(TemporalType.DATE)
	private LocalDateTime last_login;

	@Temporal(TemporalType.DATE)
	private LocalDateTime modified;
	
	private List<PhonesModel> phones;
	
	@Id
//	@SequenceGenerator(name="usu_seq", sequenceName="usu_seq", allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="usu_seq")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "USU_COD")
	public UUID getUsuCod() {
		return usuCod;
	}

	public void setUsuCod(UUID usuCod) {
		this.usuCod = usuCod;
	}

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="USU_COD")
	public List<PhonesModel> getPhones() {
		return phones;
	}

	public void setPhones(List<PhonesModel> phones) {
		this.phones = phones;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "CREATED")
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Column(name = "LAST_LOGIN")
	public LocalDateTime getLast_login() {
		return last_login;
	}

	public void setLast_login(LocalDateTime last_login) {
		this.last_login = last_login;
	}
	@Column(name = "MODIFIED")
	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}	
	
}
