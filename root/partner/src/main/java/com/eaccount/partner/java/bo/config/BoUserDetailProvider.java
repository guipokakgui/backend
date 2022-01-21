package com.eaccount.partner.java.bo.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsitm.ustra.java.core.utils.UstraNetUtils;
import com.gsitm.ustra.java.management.services.UstraAuthorityGroupService;
import com.gsitm.ustra.java.management.services.UstraAuthorityService;
import com.gsitm.ustra.java.management.services.UstraUserService;
import com.gsitm.ustra.java.management.models.UstraAuthorityGroupModel;
import com.gsitm.ustra.java.management.models.UstraUserModel;
import com.gsitm.ustra.java.mvc.utils.UstraWebUtils;
import com.gsitm.ustra.java.security.authentication.request.token.UstraAuthenticationRequestToken;
import com.gsitm.ustra.java.security.authentication.user.UstraAnonymousUser;
import com.gsitm.ustra.java.security.authentication.user.UstraUser;
import com.gsitm.ustra.java.security.authentication.user.UstraUserDetailProvider;
import com.gsitm.ustra.java.security.jwt.authentication.authentication.UstraJwtAuthentication;

public class BoUserDetailProvider implements UstraUserDetailProvider<UstraAuthenticationRequestToken, UstraJwtAuthentication> {

	@Autowired private UstraUserService userService;
	@Autowired private UstraAuthorityGroupService authorityGroupService;
	@Autowired private UstraAuthorityService authorityService;

	@Autowired private BoProperties boProperties;

	@Override
	public UstraUser getUserDetails(UstraAuthenticationRequestToken token) {

		return getBoUser(token.getUserName());
	}

	@Override
	public UstraUser getUserDetails(UstraJwtAuthentication authentication) {

		if (authentication == null || authentication.getClaims() == null
				|| StringUtils.isEmpty(authentication.getClaims().getSubject())) {
			return new UstraAnonymousUser(new ArrayList<>());
		}

		return getBoUser(authentication.getName());
	}

	private UstraUser getBoUser(String userName) {

		UstraUserModel user = userService.getUser(userName);

		if (user == null) {
			return null;
		}

		// 미승인 시에는 로그인 불가
		if (!"Y".equals(user.getApvCmplYn())) {
			user = null;
		}

		boolean isPrivacyInfoManager = "10".equals(user.getUseDvCd());

		if (!isPrivacyInfoManager) {
			// 사용자의 권한 그룹 목록 조회
			List<UstraAuthorityGroupModel> groups = authorityGroupService.getAvailableGroupsOfUser(user.getUsrId());
			isPrivacyInfoManager = groups.stream().anyMatch(gr -> "Y".equals(gr.getPrvTrtYn()));
		}

		BoSystemUser userDetail = new BoSystemUser(user.getUsrId(), user.getPwd(), this.isEnabledUser(user),
				this.isNonExpiredUser(user), this.isCredentialsNonExpiredUser(user), this.isAccountNonLockedUser(user),
				authorityService.getAllUserAuthorites(user.getUsrId()));

		userDetail.setCphNo(user.getCphNo());
		userDetail.setDeptCd(user.getDeptCd());
		userDetail.setDeptNm(user.getDeptNm());
		userDetail.setDisplayName(user.getUsrNm());
		userDetail.setUserId(user.getUsrId());
		userDetail.setPrivacyInfoManager(isPrivacyInfoManager);
		userDetail.setPwdLstUpdDttm(user.getPwdLstUpdDttm());
		userDetail.setLoginFailCnt(user.getLoginFailCnt());
		userDetail.setResetPassword("Y".equals(user.getPwdResetYn()));

		// IP 체크
		if ("Y".equals(user.getIpLmtYn()) && StringUtils.isNotEmpty(user.getIpList())) {

			final String clientIp = UstraWebUtils.getClientIp();

			if (!UstraNetUtils.isLocalAreaIp(clientIp)) {
				if (!Stream.of(user.getIpList().split(","))
						.anyMatch(ipAddr -> UstraNetUtils.checkIPMatching(ipAddr, clientIp))) {
					userDetail.setConnectedRegionAllowed(false);
				}
			}
		}

		return userDetail;
	}


	/**
	 * 활성화 된 사용자인지 체크
	 *
	 * @param user
	 * @return
	 */
	private boolean isEnabledUser(final UstraUserModel user) {

		// 탈퇴가 아닐 경우는 활성화
		return !"02".equals(user.getUsrSttCd());
	}

	/**
	 * 만료되지 않은 사용자인지 체크
	 *
	 * @param user
	 * @return
	 */
	private boolean isNonExpiredUser(final UstraUserModel user) {

		// 사용 기간 체크
		String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		if (StringUtils.isNotEmpty(user.getUseSrtDt())) {
			if (user.getUseSrtDt().compareTo(currentDate) > 0) {
				return false;
			}
		}

		if (StringUtils.isNotEmpty(user.getUseEndDt())) {
			if (user.getUseEndDt().compareTo(currentDate) < 0) {
				return false;
			}
		}

		return !"03".equals(user.getUsrSttCd());
	}

	/**
	 * 패스워드가 만료되지 않은 사용자인지 확인
	 *
	 * @param user
	 * @return
	 */
	private boolean isCredentialsNonExpiredUser(final UstraUserModel user) {
		// TODO: 비밀번호 만료 기간 추가
		return "N".equals(user.getPwdResetYn());
	}

	/**
	 * 계정이 블락되지 않은 사용자인지 확인
	 *
	 * @param user
	 * @return
	 */
	private boolean isAccountNonLockedUser(final UstraUserModel user) {
		return !"04".equals(user.getUsrSttCd());
	}
}
