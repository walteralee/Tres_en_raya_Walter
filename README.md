# Tres_en_raya_Walter

Juego clásico de Tres en Raya (Tic Tac Toe) desarrollado en Android Studio con Java.

## Características

* Modo jugador vs jugador.
* Modo jugador vs máquina.
* Interfaz simple y rápida.
* Recursos gráficos personalizados.
* Compatible con Android.

---

# Instalación y ejecución paso a paso

## 1. Instalar Android Studio

Descarga Android Studio desde:

[https://developer.android.com/studio](https://developer.android.com/studio)

Instálalo normalmente dejando las opciones por defecto.

Durante la instalación:

* Instala Android SDK.
* Instala Android Emulator.
* Instala Gradle.

Cuando termine, reinicia el PC si Android Studio lo recomienda.

---

# 2. Instalar Git

Descarga Git desde:

[https://git-scm.com/downloads](https://git-scm.com/downloads)

Instálalo con las opciones por defecto.

Para comprobar que funciona, abre CMD y ejecuta:

```bash
git --version
```

---

# 3. Clonar el proyecto

Abre CMD en la carpeta donde quieras guardar el proyecto.

Ejemplo:

```bash
cd D:\Informatica
```

Clona el repositorio:

```bash
git clone https://github.com/walteralee/Tres_en_raya_Walter.git
```

Entra a la carpeta:

```bash
cd Tres_en_raya_Walter
```

---

Puedes abrir rápidamente el proyecto ejecutando el archivo `correr.bat`.

# 4. Abrir el proyecto en Android Studio

1. Abre Android Studio.
2. Pulsa:

```text
Open
```

3. Selecciona la carpeta:

```text
Tres_en_raya_Walter
```

4. Espera a que Android Studio:

* Descargue Gradle.
* Sincronice dependencias.
* Configure el SDK.

La primera vez puede tardar varios minutos.

---

# 5. Solucionar errores comunes de Gradle

Si aparecen errores relacionados con:

```text
.gradle
build
Gradle cache
```

Haz lo siguiente:

Cerrar Android Studio.

Abrir CMD como administrador.

Ejecutar:

```bash
taskkill /F /IM java.exe
taskkill /F /IM javaw.exe
```

Luego:

```bash
rmdir /s /q C:\Users\Usuario\.gradle
```

Abrir Android Studio nuevamente.

Gradle se descargará automáticamente otra vez.

---

# 6. Ejecutar la aplicación

## Opción A — Emulador Android

1. En Android Studio:

```text
Tools → Device Manager
```

2. Crear un dispositivo virtual.
3. Elegir una versión Android.
4. Iniciar el emulador.

Cuando el emulador esté abierto:

Pulsa el botón:

```text
Run ▶
```

---

## Opción B — Teléfono Android real

### Activar modo desarrollador

En el teléfono:

```text
Ajustes → Información del teléfono → Número de compilación
```

Pulsa 7 veces.

---

### Activar depuración USB

Luego:

```text
Ajustes → Opciones de desarrollador → Depuración USB
```

Activarla.

---

### Conectar el teléfono

1. Conecta el móvil por USB.
2. Acepta la depuración USB.
3. Android Studio detectará el dispositivo.
4. Pulsa:

```text
Run ▶
```

La aplicación se instalará automáticamente.

---

# 7. Compilar APK

Para generar el APK:

```text
Build → Build APK(s)
```

Android Studio mostrará la ubicación del archivo APK.

---

# Tecnologías utilizadas

* Java
* Android SDK
* Android Studio
* Gradle
* XML

---

# Estructura del proyecto

```text
app/
 ├── src/
 │    ├── main/
 │    │    ├── java/
 │    │    ├── res/
 │    │    └── AndroidManifest.xml
 ├── build.gradle.kts
```

---

# Autor

Walter Alee

GitHub:

[https://github.com/walteralee](https://github.com/walteralee)

---

# Licencia

Proyecto de uso educativo y personal.
