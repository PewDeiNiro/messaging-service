package com.pewde.messagingservice.entity;

import com.pewde.messagingservice.enums.DialogType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "messaging", name = "dialogs")
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DialogType type;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            schema = "messaging",
            name = "conversations",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> collocutors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            schema = "messaging",
            name = "conversation_admins",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> admins;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dialog")
    private List<Message> messages;

}
