package whospeo.github.io.component;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import whospeo.github.io.rust.RustData;

public class ModComponents {

    public static final DataComponentType<RustData> RUST_DATA = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath("rusting-tools", "rust_data"),
            DataComponentType.<RustData>builder()
                    .persistent(RustData.CODEC)
                    .build()

    );

    private ModComponents() {}

    public static void init() {}
}
