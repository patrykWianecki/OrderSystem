package com.app.service.file;


public interface FileDataOperations {
    void addDataToDB();

    void addCategoryFromFileToDB();

    void addCountryFromFileToDB();

    void addCustomerFromFileToDB();

    void addProductFromFileToDB();

    void addProducerFromFileToDB();

    void addOrdersFromFileToDB();

    void deleteDataFromDB();

    void deleteCategoriesFromDB();

    void deleteCountriesFromDB();

    void deleteCustomersFromDB();

    void deleteProductsFromDB();

    void deleteProducersFromDB();

    void deleteOrdersFromDB();
}
