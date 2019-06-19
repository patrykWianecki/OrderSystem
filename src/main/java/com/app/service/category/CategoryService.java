package com.app.service.category;

import com.app.model.Category;
import com.app.model.Order;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    Map<Category, List<Order>> sortedCategoriesWithOrders();

    Category findMostPopularCategory();
}
