package com.example.jpastudent;

import java.util.*;

public interface AddressDAO {
    void insertAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(Long addressId);
    Address getAddress(Long addressId);
    List<Address> getAllAddresses(int pageNumber, int pageSize);
}
