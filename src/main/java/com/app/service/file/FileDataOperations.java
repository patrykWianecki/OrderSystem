package com.app.service.file;

public interface FileDataOperations {

    void addDataToDb();

    void addCategoryFromFileToDb();

    void addCountryFromFileToDb();

    void addCustomerFromFileToDb();

    void addProductFromFileToDb();

    void addProducerFromFileToDb();

    void addOrdersFromFileToDb();

    void deleteDataFromDB();

    void deleteCategoriesFromDB();

    void deleteCountriesFromDB();

    void deleteCustomersFromDB();

    void deleteProductsFromDB();

    void deleteProducersFromDB();

    void deleteOrdersFromDB();
}
