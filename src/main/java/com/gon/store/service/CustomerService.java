package com.gon.store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gon.store.dto.CustomerRequestDTO;
import com.gon.store.dto.CustomerResponseDTO;
import com.gon.store.dto.SaleResponseDTO;
import com.gon.store.entity.Customer;
import com.gon.store.entity.Sale;
import com.gon.store.entity.VideoGame;
import com.gon.store.exception.EmailDuplicateException;
import com.gon.store.exception.EntityNotFoundException;
import com.gon.store.repository.CustomerRepository;
import com.gon.store.repository.SaleRepository;

@Service 
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public CustomerService(CustomerRepository customerRepository, SaleRepository saleRepository){
        this.customerRepository = customerRepository;
        this.saleRepository =saleRepository;
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO){
        if (customerRepository.existsByEmail(customerRequestDTO.email())) {
            throw new EmailDuplicateException();
        }
        Customer customer = customerRepository.save(toEntity(customerRequestDTO));
        return toResponse(customer);
    }

    public CustomerResponseDTO findById(Long id){
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer", id));

        return toResponse(customer);
    }

    public Customer findEntityById(Long id){
        return customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer", id));
    }

    public List<SaleResponseDTO> findAllSales(Long idCustomer){
        if (!customerRepository.existsById(idCustomer)) {
            throw new EntityNotFoundException("Customer", idCustomer);
        }
        return saleRepository.findByCustomerId(idCustomer).stream()
            .map(this::toResponseSale).
            toList();
    }

    public void updateCustomer(Long id, CustomerRequestDTO customerRequestDTO){
        Customer customer = findEntityById(id);

        customer.setName(customerRequestDTO.name());
        customer.setEmail(customerRequestDTO.email());
        customer.setPhoneNumber(customerRequestDTO.phoneNumber());
        
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id){
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer", id);
        }

        customerRepository.deleteById(id);
    }

    private SaleResponseDTO toResponseSale(Sale sale){
        return new SaleResponseDTO(
            sale.getId(), 
            sale.getSaleDate(), 
            sale.getCustomer().getName(), 
            sale.getVideoGames().stream().map(VideoGame::getTitle).toList(), 
            sale.getTotal()
        );
    }

    private Customer toEntity(CustomerRequestDTO customer){
        return new Customer(
            null,
            customer.name(),
            customer.email(),
            customer.phoneNumber());
    }

    private CustomerResponseDTO toResponse(Customer customer){
        return new CustomerResponseDTO(
            customer.getId(), 
            customer.getName(), 
            customer.getEmail(), 
            customer.getPhoneNumber(), 
            customer.getRegistrationDate()
        );
    }

}
