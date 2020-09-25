package com.waracle.cakemgr.model.builder;

import com.waracle.cakemgr.model.CakeModel;

import java.util.UUID;

public final class CakeModelBuilder {
    private UUID id;
    private String title;
    private String desc;
    private String image;

    private CakeModelBuilder() {
    }

    public static CakeModelBuilder aCakeModel() {
        return new CakeModelBuilder();
    }

    public CakeModelBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public CakeModelBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CakeModelBuilder withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public CakeModelBuilder withImage(String image) {
        this.image = image;
        return this;
    }

    public CakeModel build() {
        CakeModel cakeModel = new CakeModel();
        cakeModel.setId(id);
        cakeModel.setTitle(title);
        cakeModel.setDesc(desc);
        cakeModel.setImage(image);
        return cakeModel;
    }
}
