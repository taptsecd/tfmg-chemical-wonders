{
  description = "TFMG: Chemical Wonders development environment";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/4feb8eb8bf30f323a8a5d285f14ee51d6a7197b1";

  outputs = { self, nixpkgs }:
    let
      systems = [ "x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin" ];
      forAllSystems = nixpkgs.lib.genAttrs systems;
    in {
      devShells = forAllSystems (system:
        let
          pkgs = import nixpkgs { inherit system; };
          gradle = pkgs.gradle_8.override { java = pkgs.jdk21; };
        in {
          default = pkgs.mkShell {
            packages = [ pkgs.jdk21 gradle pkgs.git ];
            JAVA_HOME = "${pkgs.jdk21}";
            shellHook = ''
              echo "TFMG: Chemical Wonders — Java 21 / Gradle"
              echo "Build: gradle build | Client: gradle runClient"
            '';
          };
        });
    };
}
