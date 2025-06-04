package com.noder.restapi.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.noder.restapi.models.Privilege;
import com.noder.restapi.models.Role;
import com.noder.restapi.models.UserEntity;
import com.noder.restapi.repositories.PrivilegeRepository;
import com.noder.restapi.repositories.RoleRepostiory;
import com.noder.restapi.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Component
public class DataLoader {
    private final UserRepository userRepository;
    private final RoleRepostiory roleRepostiory;
    private final PrivilegeRepository privilegeRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public DataLoader(UserRepository userRepository, RoleRepostiory roleRepostiory, PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepostiory = roleRepostiory;
        this.privilegeRepository = privilegeRepository;
    }

    @PostConstruct
    @Transactional
    public void loadData() {
        // User privileges
        Privilege readOwnTransactions = createPrivilegeIfNotFound("READ_OWN_TRANSACTIONS"); // Read the charging sessions info
        Privilege readOwnAccountInfo = createPrivilegeIfNotFound("READ_OWN_ACCOUNT_INFO");  // (name, email, etc.)
        Privilege updateOwnAccountInfo = createPrivilegeIfNotFound("UPDATE_OWN_ACCOUNT_INFO"); // Update the account info
        Privilege readChargerInfo = createPrivilegeIfNotFound("READ_CHARGER_INFO"); // Read charger information and disponibility
        Privilege reserveCharger = createPrivilegeIfNotFound("RESERVE_CHARGER"); // Reserve a charger
        
        // Sub admin privileges
        Privilege readOwnTransactionsAdmin = createPrivilegeIfNotFound("READ_OWN_TRANSACTIONS_ADMIN"); // Read the charging sessions info of all chargers assosiated with him
        Privilege manageCharger = createPrivilegeIfNotFound("MANAGE_CHARGER"); // Control the charger (turn on/off)

        // Admin privileges
        Privilege changeElectricityPrice = createPrivilegeIfNotFound("CHANGE_ELECTRICITY_PRICE"); // Change the price of electricity
        Privilege createSubordinatedSubAdmin = createPrivilegeIfNotFound("CREATE_SUBORDINATED_SUB_ADMIN"); // Create a subordinated sub admin
        Privilege deleteSubordinatedSubAdmin = createPrivilegeIfNotFound("DELETE_SUBORDINATED_SUB_ADMIN"); // Delete a subordinated sub admin
        Privilege updateSubordinatedSubAdmin = createPrivilegeIfNotFound("UPDATE_SUBORDINATED_SUB_ADMIN"); // Update a subordinated sub admin
        Privilege registerOwnCharger = createPrivilegeIfNotFound("REGISTER_OWN_CHARGER"); // Register a charger
        Privilege deleteOwnCharger = createPrivilegeIfNotFound("DELETE_OWN_CHARGER"); // Delete a charger
        
        // System admin privileges
        Privilege readAllTransactions = createPrivilegeIfNotFound("READ_ALL_TRANSACTIONS"); // Read all the charging sessions info
        Privilege readAllChargers = createPrivilegeIfNotFound("READ_ALL_CHARGERS"); // Read all the charger information
        Privilege createAllAccounts = createPrivilegeIfNotFound("CREATE_ALL_ACCOUNTS"); // Create an account
        Privilege deleteAllAccounts = createPrivilegeIfNotFound("DELETE_ALL_ACCOUNTS"); // Delete an account
        Privilege updateAllAccounts = createPrivilegeIfNotFound("UPDATE_ALL_ACCOUNTS"); // Update an account
        Privilege readAllAccounts = createPrivilegeIfNotFound("READ_ALL_ACCOUNTS"); // Read all the account information

        List<Privilege> userPrivileges = List.of(readOwnTransactions, readOwnAccountInfo, updateOwnAccountInfo, readChargerInfo, reserveCharger);
        List<Privilege> subAdminPrivileges = List.of(readOwnTransactionsAdmin, manageCharger);
        List<Privilege> adminPrivileges = new ArrayList<>();
        adminPrivileges.addAll(List.of(changeElectricityPrice, createSubordinatedSubAdmin, deleteSubordinatedSubAdmin, updateSubordinatedSubAdmin, registerOwnCharger, deleteOwnCharger));
        adminPrivileges.addAll(subAdminPrivileges);
        List<Privilege> systemAdminPrivileges = List.of(readAllTransactions, readAllChargers, createAllAccounts, deleteAllAccounts, updateAllAccounts, readAllAccounts);
    
        createRoleIfNotFound("ROLE_USER", userPrivileges);
        // createRoleIfNotFound("ROLE_SUB_ADMIN", subAdminPrivileges);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);

        Role roleSysAdmin = createRoleIfNotFound("ROLE_SYS_ADMIN", systemAdminPrivileges);
    
        createSysAdminIfNotFound(List.of(roleSysAdmin));
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, List<Privilege> privileges) {
        Role role = roleRepostiory.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepostiory.save(role);
        }
        return role;
    }

    @Transactional
    UserEntity createSysAdminIfNotFound(List<Role> roles) {
        UserEntity sysAdminUser = userRepository.findByEmail("admin@admin").orElse(null);
        if (sysAdminUser == null) {
            sysAdminUser = new UserEntity();
            sysAdminUser.setFirstName("System");
            sysAdminUser.setLastName("Admin");
            sysAdminUser.setEmail("admin@admin");
            sysAdminUser.setPin(encoder.encode("1111"));
            sysAdminUser.setPhone("12345678");
            sysAdminUser.setRoles(roles);
            System.out.println("Encoded password: " + sysAdminUser.getPin());
            userRepository.save(sysAdminUser);
        }
        return sysAdminUser;
    }
}


