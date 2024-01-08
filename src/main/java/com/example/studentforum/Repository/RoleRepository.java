package com.example.studentforum.Repository;

import com.example.studentforum.Model.Role;
import com.example.studentforum.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("SELECT u FROM Role u WHERE (u.roleid = ?1) ")
    Role getRoleUser(int Id);
}
