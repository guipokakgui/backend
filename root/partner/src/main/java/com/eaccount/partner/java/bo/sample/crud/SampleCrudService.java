package com.eaccount.partner.java.bo.sample.crud;

import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.gsitm.ustra.java.data.domains.PaginationList;
import com.gsitm.ustra.java.data.exception.UstraDataResponseCode;
import com.gsitm.ustra.java.data.validation.CrudGroups;

@Service
@Validated
@Transactional
public class SampleCrudService {

	@Autowired private SampleCrudRepository sampleCrudRepository;

	/**
	 * 코드 페이징 목록 조회
	 * @param pagination 페이징 요청 정보
	 * @param criteria 검색 조건
	 * @return 코드 목록
	 */
	public PaginationList<SampleCrudModel> getList(SampleCrudModel.Criteria criteria) {
		return sampleCrudRepository.select(criteria.getPaginationRequest(), criteria);
	}

	/**
	 * 코드 그룹 목록 조회
	 * @return 코드 목록
	 */
	public List<SampleCrudModel> getGroups() {
		return sampleCrudRepository.selectGroups();
	}

	/**
	 * 코드 상세 정보 조회
	 * @param criteria 검색 조건
	 * @return 코드 정보
	 */
	public SampleCrudModel get(@ConvertGroup(to=CrudGroups.Detail.class) @Valid SampleCrudModel criteria) {
		return sampleCrudRepository.selectDetail(criteria);
	}

	/**
	 * 코드 입력
	 * @param code 입력 정보
	 * @return 입력 코드 정보
	 */
	public SampleCrudModel add(@ConvertGroup(to=CrudGroups.Add.class) @Valid SampleCrudModel code) {
		int result = sampleCrudRepository.insert(code);

		if (result < 1) {
			throw UstraDataResponseCode.CANNOT_SAVE_RECORD.exception();
		}

		return code;
	}

	/**
	 * 코드 수정
	 * @param code 수정 정보
	 * @return 수정 코드 정보
	 */
	public SampleCrudModel edit(@ConvertGroup(to=CrudGroups.Edit.class) @Valid SampleCrudModel code) {
		int result = sampleCrudRepository.update(code);

		if (result < 1) {
			throw UstraDataResponseCode.CANNOT_SAVE_RECORD.exception();
		}

		return code;
	}

	/**
	 * 코드 삭제
	 * @param code 삭제 정보
	 * @return 삭제 코드 정보
	 */
	public SampleCrudModel remove(@ConvertGroup(to=CrudGroups.Remove.class) @Valid SampleCrudModel code) {
		int result = sampleCrudRepository.delete(code);

		if (result < 1) {
			throw UstraDataResponseCode.CANNOT_DELETE_RECORD.exception();
		}

		return code;
	}

}
