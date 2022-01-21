package com.eaccount.partner.java.bo.sample.crud;

import javax.validation.constraints.NotEmpty;

import com.gsitm.ustra.java.data.domains.PaginationRequest;
import com.gsitm.ustra.java.data.validation.CrudGroups;
import com.gsitm.ustra.java.management.models.base.UstraManagementBaseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SampleCrudModel extends UstraManagementBaseModel {

	/**
	 * 그룹 코드
	 */
	@NotEmpty(message = "그룹코드는 필수 값입니다.", groups = {
		CrudGroups.Detail.class,
		CrudGroups.Add.class,
		CrudGroups.Edit.class,
		CrudGroups.Remove.class
	})
	private String grpCd;

	/**
	 * 상세 코드
	 */
	@NotEmpty(message = "상세코드는 필수 값입니다.", groups = {
		CrudGroups.Detail.class,
		CrudGroups.Add.class,
		CrudGroups.Edit.class,
		CrudGroups.Remove.class
	})
	private String dtlCd;

	/**
	 * 코드 명
	 */
	@NotEmpty(message = "코드명은 필수 값입니다.", groups = {
		CrudGroups.Add.class,
		CrudGroups.Edit.class
	})
	private String cdNm;

	/**
	 * 코드 상세 내용
	 */
	private String cdDesc;

	/**
	 * 사용 여부
	 */
	@NotEmpty(message = "사용여부는 필수 값입니다.", groups = {
		CrudGroups.Add.class,
		CrudGroups.Edit.class
	})
	private String useYn;

	/**
	 * 비고
	 */
	private String rmk;


	/**
	 * 조건
	 * @author keymasroy<keymasroy@gsitm.com>
	 *
	 */
	@Data
	public static class Criteria {
		/**
		 * 사용 여부
		 */
		private String useYn;

		/**
		 * 그룹 코드
		 */
		private String grpCd;

		/**
		 * 페이징 요청 정보
		 */
		private PaginationRequest paginationRequest;
	}


}
