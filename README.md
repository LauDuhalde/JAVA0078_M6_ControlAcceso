# 🔐 Sistema de Control de Acceso con Spring Security

Sistema web de autenticación y autorización basado en roles utilizando Spring Boot, Spring Security, JPA y MySQL.

---
## 👥 Equipo de Desarrollo

- **Laura Duhalde**
- **Andrea Correa**
- **Andrés Shranka**

## 📋 Descripción del Proyecto

Sistema básico de control de acceso que implementa:
- ✅ Autenticación de usuarios con formulario de login
- ✅ Autorización basada en roles (USER y ADMIN)
- ✅ Encriptación de contraseñas con BCrypt
- ✅ Persistencia de datos con JPA y MySQL
- ✅ Interfaz web con Thymeleaf y Bootstrap 5

---

## 📁 Estructura del Proyecto

```
JAVA0078_M6_ControlAcceso/
│
├── src/main/java/cl/web/
│   ├── controllers/
│   │   └── WebController.java           # Controlador principal
│   ├── models/
│   │   └── Usuario.java                 # Entidad JPA
│   ├── repositories/
│   │   └── UsuarioRepository.java       # Repositorio JPA
│   ├── services/
│   │   ├── UsuarioService.java          # Interfaz del servicio
│   │   └── UsuarioServiceImpl.java      # Implementación + UserDetailsService
│   ├── dto/
│   │   └── UsuarioDTO.java              # Data Transfer Object
│   ├── security/
│   │   └── SecurityConfig.java          # Configuración de Spring Security
│   └── config/
│       └── PasswordEncoderConfig.java   # Configuración de BCrypt
│
├── src/main/resources/
│   ├── templates/
│   │   ├── index.html                   # Página principal
│   │   ├── login.html                   # Formulario de login
│   │   ├── register.html                # Formulario de registro
│   │   ├── perfil/
│   │   │   ├── panel.html              # Panel de usuario
│   │   │   └── perfil.html             # Perfil de usuario
│   │   └── admin/
│   │       └── admin.html              # Panel de administración
│   └── application.properties           # Configuración de la aplicación
│
└── pom.xml                              # Dependencias Maven
```

---

## 🗄️ Modelo de Datos

### Tabla: `usuarios`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| username | VARCHAR (PK) | Nombre de usuario único |
| password | VARCHAR | Contraseña encriptada con BCrypt |
| role | VARCHAR | Rol del usuario (USER, ADMIN) |

---

## 👤 Registro de Usuarios

Para utilizar el sistema, debes registrarte primero:

1. Accede a la página de registro: `http://localhost:8081/registro`
2. Completa el formulario con:
    - **Username:** Tu nombre de usuario único
    - **Password:** Mínimo 6 caracteres
    - **Rol:** Selecciona USER o ADMIN
3. Click en "Registrarse"
4. Inicia sesión con tus credenciales en: `http://localhost:8081/login`

> **Nota:** La primera vez que uses el sistema, deberás crear al menos un usuario administrador para acceder al panel de administración.

---

## 🔒 Configuración de Seguridad

### Rutas Públicas (sin autenticación)
- `/` - Página principal
- `/login` - Formulario de login
- `/registro` - Formulario de registro

### Rutas Protegidas (requieren autenticación)
- `/panel` - Panel general de usuario
- `/perfil` - Perfil del usuario autenticado

### Rutas Exclusivas para Administradores
- `/admin/**` - Panel de administración

---

## 🎨 Características de la Interfaz

### Página Principal
- Diseño moderno con gradientes
- Botones adaptativos según estado de autenticación
- Cards informativos sobre las características del sistema

### Panel de Usuario
- Tarjetas interactivas con efectos hover
- Acceso rápido al perfil
- Botón de administración (solo visible para ADMIN)

### Perfil de Usuario
- Visualización de información del usuario
- Avatar circular estilizado
- Badges de roles con colores distintivos

### Panel de Administración
- Tabla responsive con todos los usuarios
- Estadísticas visuales del sistema
- Filtrado y visualización de roles

---

## 🔐 Proceso de Autenticación

1. **Usuario ingresa credenciales** → Formulario `/login`
2. **Spring Security valida** → `UsuarioServiceImpl.loadUserByUsername()`
3. **Busca en base de datos** → `UsuarioRepository.findByUsername()`
4. **Compara contraseñas** → BCrypt verifica el hash
5. **Crea sesión autenticada** → `Authentication` en `SecurityContextHolder`
6. **Redirige a panel** → `/panel`

---

## 🛡️ Proceso de Autorización

```
Usuario autenticado → Intenta acceder a /admin
                    ↓
Spring Security verifica el rol
                    ↓
            ¿Tiene rol ADMIN?
            ↙           ↘
          SÍ           NO
          ↓             ↓
    Permite acceso   Deniega (403)
```

---

## 📝 Endpoints Principales

| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| GET | `/` | Página principal | Público |
| GET | `/login` | Formulario de login | Público |
| POST | `/login` | Procesar login | Público |
| GET | `/registro` | Formulario de registro | Público |
| POST | `/registro` | Crear nuevo usuario | Público |
| GET | `/panel` | Panel de usuario | Autenticado |
| GET | `/perfil` | Ver perfil | Autenticado |
| GET | `/admin` | Panel de administración | ADMIN |
| POST | `/logout` | Cerrar sesión | Autenticado |

---

## 🧪 Pruebas del Sistema

### Caso 1: Registro de Usuario
1. Ir a `http://localhost:8081/registro`
2. Completar formulario con username, password y rol
3. Click en "Registrarse"
4. Verificar redirección a `/login`

### Caso 2: Login Exitoso
1. Ir a `http://localhost:8081/login`
2. Ingresar credenciales válidas
3. Verificar redirección a `/panel`
4. Verificar que el navbar muestre el username

### Caso 3: Control de Acceso
1. Intentar acceder a `/admin` sin ser ADMIN
2. Verificar error 403 (Forbidden)
3. Login como ADMIN
4. Verificar acceso permitido a `/admin`

### Caso 4: Logout
1. Click en "Cerrar Sesión"
2. Verificar redirección a `/`
3. Intentar acceder a `/panel`
4. Verificar redirección automática a `/login`

---

## 📊 Arquitectura del Sistema

```
┌─────────────┐
│   Cliente   │
│  (Browser)  │
└──────┬──────┘
       │
       ↓
┌─────────────────┐
│  WebController  │ ← Controlador MVC
└────────┬────────┘
         │
         ↓
┌──────────────────┐
│UsuarioServiceImpl│ ← Lógica de negocio + UserDetailsService
└────────┬─────────┘
         │
         ↓
┌───────────────────┐
│UsuarioRepository  │ ← Acceso a datos (JPA)
└────────┬──────────┘
         │
         ↓
┌─────────────┐
│   MySQL     │ ← Base de datos
└─────────────┘
```

---
