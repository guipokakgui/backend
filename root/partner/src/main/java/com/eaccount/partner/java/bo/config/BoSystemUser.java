package com.eaccount.partner.java.bo.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.gsitm.ustra.java.management.bo.authentication.user.UstraSystemUser;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BoSystemUser extends UstraSystemUser {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BoSystemUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	/**
	 * 부서코드
	 */
	private String deptCd;

	/**
	 * 핸드폰 번호
	 */
	private String cphNo;
}
