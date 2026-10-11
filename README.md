# Tres en raya

App Android del clásico tres en raya: juega contra un amigo en el mismo móvil o contra una máquina que no se deja ganar.

<p align="center">
  <img src="imagenes/capturas/inicio.png" width="240" alt="Pantalla de inicio" />
  &nbsp;
  <img src="imagenes/capturas/contra_maquina.png" width="240" alt="Partida contra la máquina" />
  &nbsp;
  <img src="imagenes/capturas/dos_jugadores.png" width="240" alt="Partida a dos jugadores" />
</p>

<p align="center">
  <a href="https://github.com/walteralee/Tres_en_raya_Walter/releases/latest/download/TresEnRaya.apk">
    <img src="https://img.shields.io/badge/Descargar%20APK-v1.0-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Descargar APK" />
  </a>
</p>

---

## Funcionalidades

- **Dos jugadores:** dos personas se turnan en el mismo móvil (O contra X). Quién empieza se sortea en cada partida.
- **Contra la máquina:** juegas con la X contra la máquina (O). La máquina:
  - gana si puede y tapa tus jugadas ganadoras,
  - usa aperturas y trampas (esquina, centro, triángulo, L corta y L larga),
  - corta tus dobles amenazas, así que **como mucho le sacarás un empate**.
- El resultado (victoria o empate) se muestra en pantalla y a los 2 segundos empieza otra partida.

## Tecnologías

| | |
|---|---|
| Lenguaje | Java 11 |
| Plataforma | Android (mínimo Android 7.0, API 24) |
| Interfaz | XML con ConstraintLayout y Material 3 |
| Compilación | Gradle 8.7 con Android Gradle Plugin 8.6 |
| Tests | JUnit 4 |

## Instalar en tu móvil (3 pasos)

1. Desde el móvil, pulsa **[Descargar APK](https://github.com/walteralee/Tres_en_raya_Walter/releases/latest/download/TresEnRaya.apk)**.
2. Abre el archivo descargado. Si Android lo pide, permite **"Instalar apps desconocidas"** para tu navegador.
3. Pulsa **Instalar** y abre **Tres en raya**.

> Funciona en cualquier móvil con Android 7.0 o superior. No pide ningún permiso.

## Ejecutar desde el código

Para ver el código o modificarlo necesitas [Android Studio](https://developer.android.com/studio).

1. Clona el repositorio:

   ```bash
   git clone https://github.com/walteralee/Tres_en_raya_Walter.git
   ```

2. En Android Studio: **File > Open** y elige la carpeta `Tres_en_raya_Walter`. Espera a que termine la sincronización de Gradle.
3. Elige un emulador o conecta tu móvil con la depuración USB activada y pulsa **Run ▶**.

También se puede compilar desde la terminal (con el SDK de Android instalado):

```bash
./gradlew assembleDebug        # genera app/build/outputs/apk/debug/app-debug.apk
./gradlew testDebugUnitTest    # ejecuta los tests
```

En Windows usa `gradlew.bat` en lugar de `./gradlew`.

## Estructura

```text
app/src/main/java/com/example/tres_en_raya/
├── inicio.java           pantalla de inicio (elegir modo)
├── contra_amigo.java     partida a dos jugadores
├── contra_maquina.java   partida contra la máquina
├── Maquina.java          inteligencia de la máquina
└── Reglas.java           reglas del juego: ganador, empate, tablero
app/src/test/java/...     tests de Reglas y de Maquina
```

El tablero es una matriz 3×3 donde cada jugador usa un número (1 o 5). Así la suma de una línea dice qué hay en ella: 3 o 15 es tres en raya, 2 o 10 es una amenaza. Los tests juegan **todas las partidas posibles** contra la máquina y comprueban que nunca pierde ni juega en una casilla ocupada.

## Autor

**Walter Alejandro Cutiño Ledo** · [github.com/walteralee](https://github.com/walteralee)

---

## English (short version)

**Tic-tac-toe** for Android: play against a friend on the same phone or against an AI that can't be beaten.

- **Download:** [TresEnRaya.apk](https://github.com/walteralee/Tres_en_raya_Walter/releases/latest/download/TresEnRaya.apk). Open it on your phone, allow "Install unknown apps" if asked and tap Install (Android 7.0+).
- **Modes:** two players, or against the machine (it wins when it can, blocks your threats and forks, and uses classic opening traps).
- **Tech:** Java 11, Android (API 24+), Gradle, JUnit 4.
- **Run from source:** clone the repo, open it in Android Studio and press Run, or use `./gradlew assembleDebug`.
- **Author:** Walter Alejandro Cutiño Ledo · [github.com/walteralee](https://github.com/walteralee)
