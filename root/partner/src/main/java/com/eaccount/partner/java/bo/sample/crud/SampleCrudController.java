package com.eaccount.partner.java.bo.sample.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsitm.ustra.java.data.domains.PaginationList;
import com.gsitm.ustra.java.mvc.handler.resolver.JsonStringParameter;

@RestController
@RequestMapping("/api/sample/crud-code")
public class SampleCrudController {

	@Autowired private SampleCrudService sampleCrudService;

	@GetMapping("")
	PaginationList<SampleCrudModel> getCodeList(@JsonStringParameter SampleCrudModel.Criteria criteria) {
		return sampleCrudService.getList(criteria);
	}

	@GetMapping("/groups")
	List<SampleCrudModel> getCodeGroups() {
		return sampleCrudService.getGroups();
	}

	@GetMapping("/{grpCd}/{dtlCd}")
	SampleCrudModel getCode(@PathVariable String grpCd, @PathVariable String dtlCd) {
		return sampleCrudService.get(
				SampleCrudModel.builder()
					.grpCd(grpCd)
					.dtlCd(dtlCd)
					.build());
	}

//	@PostMapping("/detail2")
//	SampleCrudModel getCode2(@RequestBody SampleCrudModel criteria) {
//		return sampleCrudService.get2(criteria);
//	}

	@PostMapping("")
	SampleCrudModel add(@RequestBody SampleCrudModel code) {
		return sampleCrudService.add(code);
	}

	@PutMapping("")
	SampleCrudModel edit(@RequestBody SampleCrudModel code) {
		return sampleCrudService.edit(code);
	}

	@DeleteMapping("")
	SampleCrudModel remove(@RequestBody SampleCrudModel code) {
		return sampleCrudService.remove(code);
	}

}
