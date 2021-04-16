package com.cg.fms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.fms.dao.ContractDao;
import com.cg.fms.dto.Contract;
import com.cg.fms.exception.ContractException;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
public class ContractServiceTest {

	@Mock
	private ContractDao contractrepo;
	
	@InjectMocks
	private ContractServiceImpl contractservice;
	private Contract contract;
	private Contract contract2;
	private List<Contract> contractList;
	private Optional optional;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		String startDate = "03/05/2018";
		String endDate = "10/11/2020";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date strdate = null;
		Date enddate = null;
		try {
			strdate = df.parse(startDate);
			enddate = df.parse(endDate);
		} catch (ParseException e1) {
			System.out.println("wrong date formate");
			e1.printStackTrace();
		}
		contract = new Contract();
		
		contract.setContractNumber("101");
		contract.setQuotation(500000);
		contract.setStartDate(strdate);
		contract.setEndDate(enddate);
		contract.setAdmin(null);
		contract.setContractStatus("Approved");
		contract.setCustomer(null);
		contract.setLand(null);
		optional = Optional.of(contract);
		
		
		
		contract2 = new Contract();

		contract2.setContractNumber("102");
		contract2.setQuotation(30000);
		contract2.setStartDate(strdate);
		contract2.setEndDate(enddate);
		contract2.setAdmin(null);
		contract2.setContractStatus("pending");
		contract2.setCustomer(null);
		contract2.setLand(null);
		
		
	}
	
	@AfterEach
	public void tearDown() {
		contract = null;
		contract2 = null;
	}
	
	@Test
	@DisplayName("Contract Details add")
	void givenContractTosavethenShouldreturnSavedContract() throws ContractException{

		when(contractrepo.save(any())).thenReturn(contract);
		
		assertEquals(contract, contractservice.addContract(contract));
		verify(contractrepo, times(1)).save(any());
	}
	
	@Test
	@DisplayName("get all contract ")
	public void givenAllBlogsThenShouldreturnListOfAllBlogs() throws ContractException{
		
		contractrepo.save(contract);
		
		when(contractrepo.findAll()).thenReturn(contractList);
		
		List<Contract> contractList1 = contractservice.getAllContracts();
		
		assertEquals(contractList, contractList1);
		
		verify(contractrepo, times(1)).save(contract);
		
		verify(contractrepo, times(1)).findAll();
	}
	
	@Test
	@DisplayName("get exception for duplicate contract number ")
	public void givenContractTosaveThenShouldNotReturnSavedContract() {
	
		when(contractrepo.save(contract)).thenThrow(new RuntimeException());
		
		assertThrows(RuntimeException.class, () -> {contractservice.addContract(contract);});
		
		verify(contractrepo, times(1)).save(contract);
	}
	
	@Test
	@DisplayName("get by Id ")
	void givenContractNumberThenShouldreturnRespectiveContract() throws ContractException{
		
		when(contractrepo.findById("102")).thenReturn(Optional.of(contract2));
		
		Contract receivedContract = contractservice.getContract(contract2.getContractNumber());
		
		verify(contractrepo, times(1)).findById("102");
	}
	
	@Test
	@DisplayName("contract Details should delete")
	void givenContractNumberThenShouldDeleteRespectiveContract()throws ContractException{
		
		
		contractrepo.save(contract);
		 
		boolean deletedContract = contractservice.deleteContract("101");
		
		System.out.println(deletedContract);
		
		assertEquals(false, deletedContract);
		
		verify(contractrepo, times(0)).findById(contract.getContractNumber());
		
	}
	
	@Test
	@DisplayName("contract Details should update")
	public void givenContractToUpdateThenShouldReturnUpdatedContract() throws ContractException {
		
		
		when(contractrepo.existsById(contract.getContractNumber())).thenReturn(true);
		
		when(contractrepo.findById(contract.getContractNumber())).thenReturn(Optional.ofNullable(contract));
		
		when(contractrepo.save(contract)).thenReturn(contract);
		
		contract.setQuotation(400000);
		
		boolean flag = contractservice.updateContract(contract);
		
		assertEquals(true, flag);
	}
	

}
