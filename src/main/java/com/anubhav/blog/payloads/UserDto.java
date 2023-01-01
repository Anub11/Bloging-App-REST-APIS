package com.anubhav.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 4, max = 10, message = "User name must have 4 character !")
	private String name;

	@Email(message = "Email Address not valid !")
	private String email;

	@NotEmpty(message = "Please enter password")
	@Size(min = 4, message = "password must be more then 4 character !")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8, 20}$", message = "It contains at least 8 characters and at most 20 characters.\r\n"
//			+ "It contains at least one digit.\r\n" + "It contains at least one upper case alphabet.\r\n"
//			+ "It contains at least one lower case alphabet.\r\n"
//			+ "It contains at least one special character which includes !@#$%&*()-+=^.\r\n"
//			+ "It doesn’t contain any white space.")
	private String password;

	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

}
