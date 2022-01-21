package com.eaccount.partner.java.bo.sample.crud;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gsitm.ustra.java.data.domains.PaginationList;
import com.gsitm.ustra.java.data.domains.PaginationRequest;

@Mapper
public interface SampleCrudRepository {

	/**
	 * 코드 목록 조회
	 * @param pagination 페이징 요청 정보
	 * @param criteria 검색 조건
	 * @return 코드 목록
	 */
	PaginationList<SampleCrudModel> select(PaginationRequest pagination, SampleCrudModel.Criteria criteria);

	/**
	 * 코드 그룹 목록 조회
	 * @return 코드 목록
	 */
	List<SampleCrudModel> selectGroups();

	/**
	 * 코드 상세 조회
	 * @param criteria 검색 조건
	 * @return 코드 정보
	 */
	SampleCrudModel selectDetail(SampleCrudModel criteria);


	/**
	 * 코드 입력
	 * @param code 코드 정보
	 * @return 입력 건수
	 */
	int insert(SampleCrudModel code);

	/**
	 * 코드 수정
	 * @param code 코드 정보
	 * @return 수정 건수
	 */
	int update(SampleCrudModel code);


	/**
	 * 코드 삭제
	 * @param code 코드 정보
	 * @return 삭제 건수
	 */
	int delete(SampleCrudModel code);

}
