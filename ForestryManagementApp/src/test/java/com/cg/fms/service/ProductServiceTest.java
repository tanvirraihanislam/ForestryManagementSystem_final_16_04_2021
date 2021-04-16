package com.cg.fms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.fms.dao.AdminDao;
import com.cg.fms.dao.ProductDao;
import com.cg.fms.dto.Admin;
import com.cg.fms.dto.Product;
@SpringBootTest
public class ProductServiceTest {
	
	@Autowired
	ProductService productService;
	
	@MockBean
	ProductDao productRepo;
	private Product product;
	
	@BeforeEach
	void setUp() {
	    product = new Product();
		product.setProductId("10");
		product.setProductDescription("Alphanso");
		product.setProductName("AlphansoMango");
		product.setProductQuantity("20");
		product.setOrder(null);
	}
	
	@AfterEach
	void tearDown() {
		productRepo.deleteAll();
		product = null;
	
		
	}
	
	@Test
	@DisplayName("Product Details add")
	public void addProductservice() {
		
		when(productRepo.save(any())).thenReturn(product);
		assertEquals(product, productService.addProduct(product));
		verify(productRepo, times(1)).save(any());
	}
	
	@Test
	@DisplayName("prouct Details should update")
	public void updateProductservice() {
		Optional<Product> service = productRepo.findById("10");
		if(service.isPresent()) {
			service.get().setProductDescription("HP laptop");
			service.get().setProductName("HP");
			productRepo.save(service.get());	
		}
		Optional<Product> updatedservice = productRepo.findById("10");
		if(updatedservice.isPresent()) {
			assertThat(updatedservice.get().getProductDescription().equals("Alphanso"));
		}
	}
	
	
	@Test
	@DisplayName("get by Id ")
	public void getProductServiceById() {
		Optional<Product> service = productRepo.findById("10");;
		if(service.isPresent()) {
			assertEquals(service.get().getProductId(), "10");
		}
	}
	
	
	@Test
	@DisplayName("get all products ")
	public void getAllProductservice() {
		Mockito.when(productRepo.findAll())
		.thenReturn(java.util.stream.Stream.of(new Product(),new Product()).collect(Collectors.toList()));
		
		assertEquals(2, productService.getAllProducts().size());
		verify(productRepo, times(1)).findAll();
		
	}
	
	@Test
	@DisplayName("product Details should delete")
	public void deleteProductservice() {
		String ServiceId = "10";
		productService.deleteProduct(ServiceId);
				
		verify(productRepo, times(0)).deleteById( ServiceId);
	}

}