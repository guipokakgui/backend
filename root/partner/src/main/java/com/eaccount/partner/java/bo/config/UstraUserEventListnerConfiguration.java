package com.eaccount.partner.java.bo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gsitm.ustra.java.core.utils.UstraMapUtils;
import com.gsitm.ustra.java.data.utils.ProcedureManager;
import com.gsitm.ustra.java.management.services.listeners.UstraUserServiceEventListener;
import com.gsitm.ustra.java.management.models.UstraUserModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class UstraUserEventListnerConfiguration {

	@Autowired ProcedureManager spManager;

	@Bean
	public UstraUserServiceEventListener ustraUserServiceEventListener() {
		return new UstraUserServiceEventListener() {
			@Override
			public void listen(UserEventArg arg) {
				final Map<String, Object> param = new HashMap<>();


				UstraUserModel user = arg.getCurrentUser();
				UstraUserModel prevUser = arg.getPreviousUser();
				String[] phNoArray = null;
				String[] cphNoArray = null;

				//  요청구분코드값 C:등록  U:수정 D:삭제
				if(EventType.NEW.equals(arg.getType()) && "10".equals(user.getUseDvCd())) { // 사용자가 신규등록되고 개인정보취급자일때
					param.put("in_rqst_div_cd", "C");
				}

				if(EventType.UPDATE.equals(arg.getType()) && "01".equals(user.getUsrSttCd())) {
					if("10".equals(user.getUseDvCd()) && "10".equals(prevUser.getUseDvCd())) {
						param.put("in_rqst_div_cd", "U");
					} else if(!"10".equals(user.getUseDvCd()) && "10".equals(prevUser.getUseDvCd())) {
						param.put("in_rqst_div_cd", "D");
					} else if ("10".equals(user.getUseDvCd()) && !"10".equals(prevUser.getUseDvCd())) {
						param.put("in_rqst_div_cd", "C");
					} else if(!"10".equals(user.getUseDvCd()) && !"10".equals(prevUser.getUseDvCd())){
						param.put("in_rqst_div_cd", "U");
					}
				}else if(EventType.UPDATE.equals(arg.getType()) && "02".equals(user.getUsrSttCd()) && "10".equals(user.getUseDvCd())) {
					param.put("in_rqst_div_cd", "D");
				}else if (EventType.REMOVE.equals(arg.getType()) && "10".equals(prevUser.getUseDvCd())) {
					param.put("in_rqst_div_cd", "D");
				}

				param.put("in_user_id", user.getUsrId());
				param.put("in_user_pw", user.getPwd());
				param.put("in_emp_id", user.getEmpNo());
				param.put("in_user_nm", user.getUsrNm());
				param.put("in_rsno_head", "");
				param.put("in_rsno_fnl", "");
				param.put("in_co_cd", user.getDeptCd());
				param.put("in_eml_addr", user.getEmail());

				if(user.getPhNo() != null && !"".equals(user.getPhNo())) {
					phNoArray = user.getPhNo().split("-");
					param.put("in_head_tphn_no", phNoArray[0]);
					param.put("in_mid_tphn_no", phNoArray[1]);
					param.put("in_fnl_tphn_no", phNoArray[2]);
				}
				if(user.getCphNo() != null && !"".equals(user.getCphNo())) {
					cphNoArray = user.getCphNo().split("-");
					param.put("in_head_cphn_no", cphNoArray[0]);
					param.put("in_mid_cphn_no", cphNoArray[1]);
					param.put("in_fnl_cphn_no", cphNoArray[2]);
				}

				param.put("in_user_st_cd", user.getUsrSttCd());
				param.put("in_use_strt_dt", user.getUseSrtDt());
				param.put("in_use_end_dt", user.getUseEndDt());
				param.put("in_user_div_cd", user.getUseDvCd());
				param.put("in_rmk", user.getRmk());
				param.put("in_modr_id", user.getUpdUsrId());
				param.put("in_modr_ip", user.getUpdUsrIp());

				if(prevUser != null) {
					param.put("in_org_user_dv_cd", prevUser.getUseDvCd());
				}

				param.put("ot_respon_code", "");
				param.put("ot_res_msg", "");

				UstraMapUtils.underScoreToCamelCase(spManager.callSp("GSCDBA", "ZSP_CL_IF_MEMB_INS", param));

			}
		};
	}
}
