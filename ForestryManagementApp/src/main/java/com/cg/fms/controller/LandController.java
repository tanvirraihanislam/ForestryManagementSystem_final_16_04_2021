package com.cg.fms.controller;

import java.util.List;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.fms.dto.Land;
import com.cg.fms.dto.Product;
import com.cg.fms.exception.LandException;
import com.cg.fms.service.LandService;
import com.fasterxml.jackson.annotation.JsonIgnore;

@RestController
@RequestMapping("land")
public class LandController {
	
	@Autowired
	LandService landservice;
	
	@GetMapping("getById/{landId}")
	public ResponseEntity<?> getLand(@PathVariable("landId") String landId ){
		
		Land land = landservice.getLand(landId);
		if(land != null) {
			return new ResponseEntity<Land>(land, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<String>("land details with id  " + landId + " is not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("addNewLand/{landId}/{surveyNumber}/{ownerName}/{landArea}")
	public String addLand(@PathVariable("landId") String landId, @PathVariable("surveyNumber") String surveyNumber, @PathVariable("ownerName") String ownerName, @PathVariable("landArea") String landArea) throws LandException {
		
		Land land = new Land();
		
		land.setLandId(landId);
		land.setSurveyNumber(surveyNumber);
		land.setOwnerName(ownerName);
		land.setLandArea(landArea);
		
		if(landservice.addLand(land))
			return "New Land added";
		else
			return "land already exixts";
	}
	
	@PutMapping("{landId}")
	public String updateLand(@PathVariable("landId") String landId, String surveyNumber, String ownerName,
			String landArea) {

		Land land = new Land();
		
		land.setLandId(landId);
		land.setSurveyNumber(surveyNumber);
		land.setOwnerName(ownerName);
		land.setLandArea(landArea);
		
		if(landservice.updateLand(land))
			return "Land has been updated";
		else
			return "Land Not found";
	}
	
	@DeleteMapping("/deleteLand/{landId}")
	public String deleteLand(@PathVariable("landId") String landId) {
		if(landservice.removeLandDetails(landId))
			return "Land has been removed";
		else
			return "Land not found";
	}
	
	@GetMapping("getAllLand")
	public List<Land> getAllLand(){
		return landservice.getAllLands();
	}
	

}
