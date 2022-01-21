//package com.eaccount.partner.java.bo.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.gsitm.ustra.java.core.utils.UstraMapUtils;
//import com.gsitm.ustra.java.data.utils.ProcedureManager;
//import com.gsitm.ustra.java.management.bo.services.listeners.UstraAuthorityServiceEventListener;
//import com.gsitm.ustra.java.management.models.UstraAuthorityGroupModel;
//import com.gsitm.ustra.java.management.models.UstraMenuAuthorityModel;
//import com.gsitm.ustra.java.management.models.UstraUserModel;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Configuration
//public class UstraAuthorityEventListnerConfiguration {
//
//	/*
//	 * 1. 권한그룹 - 등록/ 삭제 (사용자 아이디, 권한그룹- prv_trt_yn // 메뉴아이디 null로 보내야함 ) - 삭제 :
//	 * prv_trt_yn : y, n - 신규 : prv_trt_yn : y
//	 *
//	 * 2. 권한 관리 - 메뉴 수정/ 삭제 (권한그룹, 메뉴 - prv_view_yn , 메뉴명도 넣어야함 /// 사용자아이디
//	 * null로보내야함) - 삭제 : prv_trt_yn : y, n - 신규 : prv_trt_yn : y
//	 */
//
//	@Autowired ProcedureManager spManager;
//
//	@Bean
//	public UstraAuthorityServiceEventListener ustraAuthorityServiceEventListener() {
//		return new UstraAuthorityServiceEventListener() {
//			@Override
//			public void listen(AuthorityEventArg arg) {
//				final Map<String, Object> param = new HashMap<>();
//
//				UstraAuthorityGroupModel authorityGroup = arg.getAuthority();
//				UstraMenuAuthorityModel authorityMenu = arg.getMenuAuthority();
//				UstraUserModel user = arg.getUser();
//
//					// 요청구분코드값 C:등록 D:삭제
//					if (EventType.NEW.equals(arg.getType())) {
//						param.put("in_rqst_div_cd", "C");
//						param.put("in_inqy_auth_yn", "Y");
//						param.put("in_mod_auth_yn", "Y");
//						param.put("in_inpt_auth_yn", "Y");
//						param.put("in_del_auth_yn", "Y");
//						param.put("in_prnt_auth_yn", "Y");
//						param.put("in_dwln_auth_yn", "Y");
//					} else if (EventType.REMOVE.equals(arg.getType())) {
//						param.put("in_rqst_div_cd", "D");
//						param.put("in_inqy_auth_yn", "N");
//						param.put("in_mod_auth_yn", "N");
//						param.put("in_inpt_auth_yn", "N");
//						param.put("in_del_auth_yn", "N");
//						param.put("in_prnt_auth_yn", "N");
//						param.put("in_dwln_auth_yn", "N");
//					}
//
//					param.put("in_group_id", authorityGroup.getAuthGrpId());
//					param.put("in_menu_id", authorityMenu == null ? "" : authorityMenu.getMnuId());
//					param.put("in_menu_nm", authorityMenu == null ? "" : authorityMenu.getMnuNm());
//					param.put("in_user_id", user == null ? "" :user.getUsrId());
//					param.put("in_etc1_auth_yn", "");
//					param.put("in_etc2_auth_yn", "");
//					param.put("in_etc3_auth_yn", "");
//					param.put("in_rmk", "");
//					param.put("in_modr_id", authorityGroup.getUpdUsrId());
//					param.put("in_modr_ip", authorityGroup.getUpdUsrIp());
//					param.put("ot_respon_code", "");
//					param.put("ot_res_msg", "");
//
//					log.info("event 발생 ++++++++++++++++");
//					UstraMapUtils.underScoreToCamelCase(spManager.callSp("DEV_WASUP", "ZSP_CL_IF_AUTH_INS", param));
//				}
//
//		};
//	}
//}
