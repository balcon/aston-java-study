package com.github.balcon.venue.repository;

import com.github.balcon.venue.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository<T extends Equipment> extends JpaRepository<T, Integer> {
}
