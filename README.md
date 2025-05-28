# Ecosistema Jamboree

**Jamboree** es un ecosistema digital desarrollado como proyecto final de un Ciclo Formativo de Grado Superior, basado en la plataforma **Odoo**. El sistema implementa una arquitectura modular, entornos diferenciados para desarrollo y producción, herramientas para la migración de datos desde sistemas legados, una aplicación externa para la consulta de información mediante la API de Odoo, y una aplicación móvil para acceso remoto.

---

## 📦 Descripción General

El ecosistema está compuesto por los siguientes componentes principales:

- **Odoo personalizado**: módulo específico adaptado a las necesidades de Jamboree.
- **Entornos de desarrollo y producción**: configurados mediante Docker y Docker Compose.
- **Migración de datos**: herramientas y pruebas para importar información desde sistemas anteriores.
- **Aplicación API externa**: conexión con Odoo mediante REST para consulta de datos.
- **Aplicación móvil**: acceso a funcionalidades clave desde dispositivos móviles.

---

## 📁 Estructura del Proyecto

```plaintext
jamboree/
├── index.html                       # Página de presentación (anexo)
├── migrarJamboree/                 # Scripts y datos para pruebas de migración
│   ├── pruebaBD.csv
│   ├── pruebaBDTutor.csv
│   └── pruebaApi.ipynb
│   └── README.md
├── odooDesarrollo/                 # Entorno de desarrollo con módulos personalizados
│   ├── docker-compose.yml
│   ├── addons/
│   ├── DiagramaClases/
│   └── README.md
├── odooProduccion/                 # Configuración para entorno de producción
│   ├── docker-compose.yml
│   └── README.md
├── jamboreeMovil/                  # Aplicación móvil conectada a Odoo
│   ├── código fuente
│   └── README.md
├── webAnexos/                      # Documentación adicional (mapa, Gantt, etc.)
└── README.md                       # Este archivo
```

---

## ⚙️ Requisitos Generales

- Docker
- Docker Compose
- Python 3 (para pruebas con notebooks y scripts)
- Entorno de desarrollo móvil (para `jamboreeMovil`)

---

## 🚀 Instrucciones Básicas

### Desarrollar en Odoo

```bash
cd odooDesarrollo
docker-compose up --build
```

### Producción

```bash
cd odooProduccion
docker-compose up -d
```

### Migración de Datos

Ejecutar el notebook `pruebaApi.ipynb` en `migrarJamboree/` para comprobar la conectividad con la API y simular carga de datos desde CSV.

### Aplicación Móvil

Abrir el proyecto en el entorno de desarrollo correspondiente (ej. Android Studio) y ejecutar en emulador o dispositivo físico.

---

## 📄 Licencia

Este proyecto está distribuido bajo la licencia MIT.
