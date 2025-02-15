package com.robertx22.mine_and_slash.aoe_data.datapacks.models;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.DirUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.item.Item;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ModelHelper {

    Item item;
    Type type;
    String tex;

    public String modelPath = "";

    public ModelHelper(Item item, Type type) {
        this.item = item;
        this.type = type;
    }

    public ModelHelper(Item item, Type type, String tex) {
        this.item = item;
        this.type = type;
        this.tex = tex;
    }

    public void generate() {

        String tex = getTextureString();
        if (this.tex != null) {
            tex = this.tex;
        }

        String parent = "";

        if (type == Type.GENERATED) {
            parent = "item/generated";
        } else {
            parent = "item/handheld";
        }

        String filecontent = getBaseJsonString();
        filecontent = filecontent.replace("[PARENT]", parent);
        filecontent = filecontent.replace("[TEXTURE]", tex);

        Path path = DirUtils.generatedResourcesDir();


        String reg = "assets/" + SlashRef.MODID + "/models/item/" + VanillaUTIL.REGISTRY.items().getKey(item)
                .getPath()
                + ".json";

        path = path.resolve(reg);

        int index = path.toString()
                .lastIndexOf("\\");

        String withoutname = path.toString()
                .substring(0, index + 1);

        try {
            new File(withoutname).mkdirs();
            File file = new File(path.toString());
            file.createNewFile();
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(filecontent);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getTextureString() {
        String id = VanillaUTIL.REGISTRY.items().getKey(item).toString();

        if (!modelPath.isEmpty()) {
            id = SlashRef.MODID + ":" + modelPath;
        }

        String tex = id.replace(SlashRef.MODID + ":", SlashRef.MODID + ":item/");

        return tex;
    }

    private String getBaseJsonString() {
        return "{\n  \"parent\": \"[PARENT]\",\n  \"textures\": {\n    \"layer0\": \"[TEXTURE]\"\n  }\n}";
    }

    public enum Type {
        GENERATED, HANDHELD
    }

}
