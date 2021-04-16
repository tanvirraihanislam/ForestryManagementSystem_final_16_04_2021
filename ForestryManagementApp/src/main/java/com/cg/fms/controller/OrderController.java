package com.cg.fms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.fms.dto.Customer;
import com.cg.fms.dto.Order;
import com.cg.fms.dto.Product;
import com.cg.fms.dto.Scheduler;
import com.cg.fms.service.CustomerService;
import com.cg.fms.service.OrderService;
import com.cg.fms.service.ProductService;
import com.cg.fms.service.SchedulerService;

@RestController
@RequestMapping("orderDetails")
public class OrderController {

	@Autowired
	OrderService orderservice;

	@Autowired
	CustomerService custServ;

	@Autowired
	SchedulerService schedServ;

	@Autowired
	ProductService prodServ;

	@GetMapping("{orderNumber}")
	public ResponseEntity<?> getOrder(@PathVariable("orderNumber") String orderNumber) {

		Order order = orderservice.getOrder(orderNumber);
		if (order == null)
			return new ResponseEntity<String>("Order with Id " + orderNumber + " not found", HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Order>(order, HttpStatus.FOUND);
	}

	@GetMapping
	public List<Order> getAllOrder() {
		return orderservice.getAllOrders();
	}

	@PostMapping("{orderNumber}/{deliveryPlace}/{deliveryDate}/{quantity}/{customerId}/{schedulerId}/{productId}")
	public String addOrder(@PathVariable("orderNumber") String orderNumber,
			@PathVariable("deliveryPlace") String deliveryPlace, @PathVariable("deliveryDate") String deliveryDate,
			@PathVariable("quantity") String quantity, @PathVariable("customerId") String customerId,
			@PathVariable("schedulerId") String schedulerId, @PathVariable("productId") String productId) {

		Order order = new Order();

		order.setOrderNumber(orderNumber);
		order.setDeliveryPlace(deliveryPlace);
		order.setDeliveryDate(deliveryDate);
		order.setQuantity(quantity);

		Customer customer = new Customer();
		customer = custServ.getCustomer(customerId);
		order.setCustomer(customer);

		Scheduler sched = new Scheduler();
		sched = schedServ.getScheduler(schedulerId);
		order.setScheduler(sched);

		Product product = new Product();
		product = prodServ.getProduct(productId);
		order.setProducts(product);

		if (orderservice.addOrder(order))
			return "New Order is Added";
		else
			return "Admin already is exist";
	}

	@PutMapping("{orderNumber}/{deliveryPlace}/{deliveryDate}/{quantity}/{customerId}/{schedulerId}/{productId}")
	public String updateOrder(@PathVariable("orderNumber") String orderNumber,
			@PathVariable("deliveryPlace") String deliveryPlace, @PathVariable("deliveryDate") String deliveryDate,
			@PathVariable("quantity") String quantity, @PathVariable("customerId") String customerId,
			@PathVariable("schedulerId") String schedulerId, @PathVariable("productId") String productId) {

		Order order = new Order();

		order.setOrderNumber(orderNumber);

		order.setDeliveryPlace(deliveryPlace);

		order.setDeliveryDate(deliveryDate);

		order.setQuantity(quantity);

		Customer customer = new Customer();
		customer = custServ.getCustomer(customerId);
		order.setCustomer(customer);

		Scheduler sched = new Scheduler();
		sched = schedServ.getScheduler(schedulerId);
		order.setScheduler(sched);

		Product product = new Product();
		product = prodServ.getProduct(productId);
		order.setProducts(product);

		if (orderservice.updateOrder(order))
			return "Order is updated";
		else
			return "Order not found";
	}

	@DeleteMapping("orderNumber")
	public String deleteOrder(@PathVariable("orderNumber") String orderNumber) {
		if (orderservice.deleteOrder(orderNumber))
			return "Order has been updated";
		else
			return "Order not found";
	}

}