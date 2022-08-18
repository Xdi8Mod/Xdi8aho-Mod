
task("fakeJar", Jar::class) {
    from("fabric.mod.json", "75e00dfd-ed5b-4626-a193-f6874736f052.png")
    into(buildDir.resolve("fake-jar"))
}
