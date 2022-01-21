package com.eaccount.partner.java.bo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gsitm.ustra.java.data.mapping.TableMapping;

@Configuration
public class TableInfoConfiguration {

	@Bean
	public GroupCommonCodeTableMappingModel groupCommonCodeTableMappingModel() {
		return new GroupCommonCodeTableMappingModel();
	}

	@TableMapping(value = "code", tableName = "USTRA_CMM_CD_TEST")
	public static class GroupCommonCodeTableMappingModel {

		public String grpCd = "CODE_ID";
		public String grpNm = "CODE_ID_NAME";
		public String dtlCd = "LVL3_CODE";
		public String cdNm = "LVL3_CODE_NAME";
//		public String cdDesc = "CD_EXPL";
		public String uprGrpCd = "UP_CODE_ID";
//		public String uprDtlCd = "UPR_DTL_CD";
		public String srtNo = "DISPLAY_ORDER";
		public String useYn = "USE_IND";
		public String rmk = "REMARK";
		public String etc1 = "ETC1";
		public String etc2 = "ETC2";
		public String etc3 = "ETC3";
		public String etc4 = "ETC4";
		public String etc5 = "ETC5";
		public String lclsCd = "LVL1_CODE";
		public String lclsNm = "LVL1_CODE_NAME";
		public String mclsCd = "LVL2_CODE";
		public String mclsNm = "LVL2_CODE_NAME";
		public String cdLen = "CODE_LEN";
		public String regDttm = "INPUT_DATE_TIME";
		public String regUsrId = "INPUT_USER_ID";
//		public String regUsrIp = "INPUT_IP";
		public String updDttm = "UPD_DATE_TIME";
		public String updUsrId = "UPD_USER_ID";
//		public String updUsrIp = "MOD_IP";

	}

}
