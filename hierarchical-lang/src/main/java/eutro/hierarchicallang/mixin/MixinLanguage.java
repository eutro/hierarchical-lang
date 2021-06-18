package eutro.hierarchicallang.mixin;

import com.google.gson.JsonObject;
import eutro.jsonflattener.JsonFlattener;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Language.class)
public class MixinLanguage {
    @ModifyVariable(
            method = "load(Ljava/io/InputStream;Ljava/util/function/BiConsumer;)V",
            ordinal = 0,
            at = @At(
                    value = "STORE",
                    ordinal = 0
            )
    )
    private static JsonObject flatten(JsonObject deserialized) {
        return JsonFlattener.flatten(deserialized);
    }
}
