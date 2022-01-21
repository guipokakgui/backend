package com.eaccount.partner.java.bo.config;

import javax.validation.constraints.NotNull;

import com.gsitm.ustra.java.management.bo.authentication.listener.ManagementAuthenticationListener;
import com.gsitm.ustra.java.management.bo.authentication.processor.ManagementBoJwtAuthenticationProcessor;
import com.gsitm.ustra.java.management.properties.UstraManagementBoProperties;
import com.gsitm.ustra.java.security.authentication.listener.UstraAuthenticationListener;
import com.gsitm.ustra.java.security.authentication.processor.UstraAuthenticationProcessor;
import com.gsitm.ustra.java.security.authentication.properties.UstraAuthenticationProperties;
import com.gsitm.ustra.java.security.authentication.request.token.UstraAuthenticationRequestToken;
import com.gsitm.ustra.java.security.authentication.user.UstraUserDetailProvider;
import com.gsitm.ustra.java.security.jwt.authentication.UstraJwtAuthenticationConfigurer;
import com.gsitm.ustra.java.security.jwt.authentication.authentication.UstraJwtAuthentication;
import com.gsitm.ustra.java.security.jwt.authentication.claims.UstraJwtClaimAppender;
import com.gsitm.ustra.java.security.jwt.authentication.processor.UstraJwtAuthenticationProcessor;
import com.gsitm.ustra.java.security.jwt.authentication.properties.UstraJwtAuthenticationProperties;
import com.gsitm.ustra.java.security.jwt.authentication.store.HttpHeaderCookieJwtAuthenticationStore;
import com.gsitm.ustra.java.security.jwt.authentication.store.UstraJwtAuthenticationStore;
import com.gsitm.ustra.java.security.jwt.authentication.store.UstraJwtRefreshTokenStore;

public class BoJwtAuthenticationProcessor extends ManagementBoJwtAuthenticationProcessor {

	public BoJwtAuthenticationProcessor(UstraManagementBoProperties properties,
			UstraJwtRefreshTokenStore refreshTokenStore) {
		super(properties, refreshTokenStore);
	}

	@Override
	protected UstraJwtAuthenticationConfigurer<UstraAuthenticationRequestToken, UstraJwtAuthentication> getConfigurer(
			UstraManagementBoProperties properties, UstraJwtRefreshTokenStore refreshTokenStore) {

		return new BoAuthenticationConfigurer(properties, refreshTokenStore);
	}

	/**
	 * Bo에서 사용하는 configurer
	 *
	 */
	public static class BoAuthenticationConfigurer implements UstraJwtAuthenticationConfigurer<UstraAuthenticationRequestToken, UstraJwtAuthentication> {

		private UstraManagementBoProperties properties;
		private UstraJwtRefreshTokenStore refreshTokenStore;

		public BoAuthenticationConfigurer(UstraManagementBoProperties properties, UstraJwtRefreshTokenStore refreshTokenStore) {
			this.properties = properties;
			this.refreshTokenStore = refreshTokenStore;
		}

		@Override
		public UstraAuthenticationProperties authenticationProperties(
				UstraAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication> processor) {
			return properties.getAuthentication();
		}

		@Override
		public @NotNull UstraUserDetailProvider<UstraAuthenticationRequestToken, UstraJwtAuthentication> userDetailProvider(
				UstraAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication> processor) {
			return new BoUserDetailProvider();
		}

		@Override
		public UstraJwtRefreshTokenStore jwtRefreshTokenStore(
				UstraAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication> processor) {
			return refreshTokenStore;
		}

		@Override
		public @NotNull UstraJwtAuthenticationStore jwtAuthenticationStore(
				UstraAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication> processor) {

			if (properties.getAuthentication().getUseCookie()) {
				return new HttpHeaderCookieJwtAuthenticationStore((UstraJwtAuthenticationProperties)processor.getAuthenticationProperties(),
						((UstraJwtAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication>)processor).getJwtClaimAppender(),
						((UstraJwtAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication>)processor).getJwtClaimChecker(),
						((UstraJwtAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication>)processor).getJwtRefreshTokenStore(),
						((UstraJwtAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication>)processor).getJwtAuthenticationParser(),
						((UstraJwtAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication>)processor).getJwtValidationFailureDecisioner());
			}

			return UstraJwtAuthenticationConfigurer.super.jwtAuthenticationStore(processor);

		}

		@Override
		public @NotNull UstraJwtClaimAppender jwtClaimAppender(
				UstraAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication> processor) {
			return new BoJwtClaimAppender();
		}

		@Override
		public UstraAuthenticationListener<UstraAuthenticationRequestToken, UstraJwtAuthentication> authenticationListener(
				UstraAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication> processor) {
			return new ManagementAuthenticationListener<>();
		}

//		@Override
//		public UstraAuthenticationKeyChecker<UstraJwtAuthentication> authenticationKeyChecker(
//				UstraAuthenticationProcessor<UstraAuthenticationRequestToken, UstraJwtAuthentication> processor) {
//			return new UstraAuthenticationKeyChecker<UstraJwtAuthentication>() {
//
//				@Override
//				public String publishKey(UstraJwtAuthentication authentication) {
//
//					// 최초 인증
//					if (authentication.getClaims() == null) {
//						return "__REQUIRED_SEND_MESSAGE__";
//					}
//					else if (authentication.getAuthenticationKey().equals("__REQUIRED_SEND_MESSAGE__")) {
//						// SMS SEND
//						return "1121";
//					}
//
//					return null;
//
//
////					BoSystemUser ustraSystemUser = (BoSystemUser)authentication.getUser();
////					ustraSystemUser.getUserId();
////					ustraSystemUser.getCphNo();
////
////					// sp호출해서 번호 받고  return 번호
////
////					return Integer.toString(ThreadLocalRandom.current().nextInt(1000, 9999));
//				}
//
//				@Override
//				public boolean checkKey(UserNameWithKeyAuthenticationRequestToken token,
//						UstraJwtAuthentication authentication) {
//
//					return StringUtils.equals(token.getAuthenticationKey(), authentication.getAuthenticationKey());
//
//				}
//			};
//		}
	}
}
