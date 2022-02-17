package com.etaskify.task_manager.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "assignees")
@Getter
@Setter
public class Assignee {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
