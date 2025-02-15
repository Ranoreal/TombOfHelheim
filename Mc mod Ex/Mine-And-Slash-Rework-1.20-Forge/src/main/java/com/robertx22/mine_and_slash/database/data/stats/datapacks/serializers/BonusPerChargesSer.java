package com.robertx22.mine_and_slash.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.AutoStatGson;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.stats.BonusStatPerEffectStacks;

// todo shouldnt i just change all this into gson?
public class BonusPerChargesSer implements IStatSerializer<BonusStatPerEffectStacks> {

    @Override
    public JsonObject statToJson(BonusStatPerEffectStacks obj) {
        JsonObject data = AutoStatGson.PARSER.parse(AutoStatGson.GSON.toJson(obj.data)).getAsJsonObject();

        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.add("core_stat_data", data);
        json.addProperty("effect_id", obj.effect);

        return json;
    }

    @Override
    public BonusStatPerEffectStacks getStatFromJson(JsonObject json) {
        CoreStatData data = AutoStatGson.GSON.fromJson(json.get("core_stat_data"), CoreStatData.class);
        String id = json.get("id").getAsString();
        StatScaling scaling = StatScaling.valueOf(json.get("scale").getAsString());
        String effect = json.get("effect_id").getAsString();
        BonusStatPerEffectStacks stat = new BonusStatPerEffectStacks(effect, id, "", scaling, data);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
