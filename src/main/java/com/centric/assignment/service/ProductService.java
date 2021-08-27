package com.centric.assignment.service;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.centric.assignment.exception.BadRequestException;
import com.centric.assignment.exception.DataNotFoundException;
import com.centric.assignment.model.Product;
import com.centric.assignment.model.ProductResponse;
import com.centric.assignment.repository.ProductEntity;
import com.centric.assignment.repository.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ProductService {

	@Autowired
	public ProductRepository repository;

	/**
	 * @apiNote Adds the Product along with id: UUID and createdAt: date and time
	 * @param product
	 * @return
	 */
	public ProductResponse insertProduct(Product product) {
		ProductEntity productEntity = ProductEntity.builder()
				.id(UUID.randomUUID().toString())
				.name(product.getName())
				.brand(product.getBrand())
				.category(product.getCategory())
				.tags(this.convertListToString(product.getTags()))
				.description(product.getDescription())
				.createdAt(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT))
				.build();

		ProductEntity result = repository.save(productEntity);

		return ProductResponse.builder()
				.id(result.getId())
				.brand(result.getBrand())
				.category(result.getCategory())
				.created_at(result.getCreatedAt())
				.description(result.getDescription())
				.name(result.getName())
				.tags(this.convertStringToList(result.getTags()))
				.build();
	}

	/**
	 * @apiNote Fetches the Product entity(s) based on the input request parameters
	 * @param page
	 * @param size
	 * @param sort
	 * @param category
	 * @return
	 */
	public List<ProductResponse> getProduct(int page, int size, String[] sort, String category) {
		List<Order> orders = new ArrayList<Order>();

		if (sort[0].contains(",")) {
			// will sort more than 2 fields
			// sortOrder="field, direction"
			for (String sortOrder : sort) {
				String[] _sort = sortOrder.split(",");
				orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
			}
		} else {
			// sort=[field, direction]
			orders.add(new Order(getSortDirection(sort[1]), sort[0]));
		}

		List<ProductResponse> tutorials = new ArrayList<ProductResponse>();
		Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
		Page<ProductEntity> pageTuts;
		
		if (category == null || category.isEmpty()) {
			throw new BadRequestException(BAD_REQUEST);	
		}
		else {
			pageTuts = repository.findByCategory(category, pagingSort);
		}
		
		if(pageTuts.isEmpty()) throw new DataNotFoundException(NOT_FOUND);
		
		tutorials = ofNullable(pageTuts.getContent())
				.filter(CollectionUtils::isNotEmpty)
				.map(productEntities -> productEntities
						.stream().map(productEntity -> ProductResponse.builder()
								.id(productEntity.getId())
								.brand(productEntity.getBrand())
								.category(productEntity.getCategory())
								.created_at(productEntity.getCreatedAt())
								.description(productEntity.getDescription())
								.name(productEntity.getName())
								.tags(this.convertStringToList(productEntity.getTags()))
								.build())
						.collect(Collectors.toList()))
				.orElse(new ArrayList<>());
		return tutorials;
	}

	private String convertListToString(List<String> tags) {
		return new Gson().toJson(tags);
	}

	private List<String> convertStringToList(String tags) {
		return new Gson()
				.fromJson(tags, new TypeToken<List<String>>() {
				}.getType());
	}

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}
		return Sort.Direction.ASC;
	}

}
