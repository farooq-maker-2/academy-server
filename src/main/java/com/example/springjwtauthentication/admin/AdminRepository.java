package com.example.springjwtauthentication.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findAdminByEmail(String email);
}