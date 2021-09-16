package com.deyuan.goods.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_spec")
public class Spec {
    @Id
    private Integer id;
    private String name;
    private String options;
    private Integer seq;
    private Integer template_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", options='" + options + '\'' +
                ", seq=" + seq +
                ", template_id=" + template_id +
                '}';
    }
}
