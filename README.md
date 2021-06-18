# Hierarchical Lang

> i just had a stupid idea
> hacky minecraft mixin that enables "hierarchical lang files"
> so instead of

```json
{
  "item.botania.black_lotus": "Black Lotus",
  "item.botania.blacker_lotus": "Blacker Lotus"
}
```

> you get something like

```json
{
  "item": {
    "botania": {
      "black_lotus": "Black Lotus",
      "blacker_lotus": "Blacker Lotus"
    }
  }
}
```

# This is great, how can I use it?

## At runtime

1. Depend on this as a mod.
2. Use your beautiful hierarchical lang jsons.

## At build/datagen time

> blursed: datagenerator that just takes the below and transforms it into the above at build time

> that's actually real easy i think

[JsonFlattener]: flattener/src/main/java/eutro/jsonflattener/JsonFlattener.java

1. Depend on [JsonFlattener] at build/datagen time.
   - You can either:
     - Just copy the code, as long as you don't disrespect the [license](LICENSE).
     - Use [JitPack](https://jitpack.io/).
     - Build locally and publish to Maven local, then depend on that.
     - Use any other cursed dependency system you want to use.
2. Flatten your generated or handwritten Json using the `flatten` method of [JsonFlattener].

> ...it does mean that datapackers don't get the same luxury though
