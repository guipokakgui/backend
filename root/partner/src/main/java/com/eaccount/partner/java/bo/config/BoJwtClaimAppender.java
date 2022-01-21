package com.eaccount.partner.java.bo.config;

import org.springframework.security.core.Authentication;

import com.gsitm.ustra.java.management.bo.authentication.claims.ManagementJwtClaimAppender;
import com.gsitm.ustra.java.security.jwt.authentication.authentication.UstraJwtAuthentication;
import com.gsitm.ustra.java.security.jwt.authentication.claims.UstraJwtClaims;

public class BoJwtClaimAppender extends ManagementJwtClaimAppender {

	@Override
	public void append(UstraJwtClaims claims, Authentication authentication) {

		super.append(claims, authentication);

		if( authentication instanceof UstraJwtAuthentication ) {
			UstraJwtAuthentication jwtAuthentication = (UstraJwtAuthentication)authentication;

			if (jwtAuthentication.getUser() instanceof BoSystemUser) {
				BoSystemUser ustraSystemUser = (BoSystemUser)jwtAuthentication.getUser();

				claims.put("deptCd", ustraSystemUser.getDeptCd());
				claims.put("cphNo", ustraSystemUser.getCphNo());
			}
		}
	}
}
