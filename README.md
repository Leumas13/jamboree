# Ecosistema Jamboree

**Jamboree** es un ecosistema digital desarrollado como proyecto final de un Ciclo Formativo de Grado Superior, basado en la plataforma **Odoo**. El sistema implementa una arquitectura modular, entornos diferenciados para desarrollo y producción, herramientas para la migración de datos desde sistemas legados, y una aplicación externa para la consulta de información mediante la API de Odoo.

## 📦 Descripción General

El ecosistema está compuesto por los siguientes elementos:

- **Odoo con módulo personalizado**: implementación de funcionalidades específicas para la operativa de Jamboree.
- **Entornos separados**: uso de Docker y Docker Compose para gestionar entornos de desarrollo y producción de forma aislada.
- **Migración de datos**: scripts y pruebas para importar datos desde fuentes CSV hacia el nuevo sistema Odoo.
- **Aplicación externa**: cliente que se conecta con la API REST de Odoo para recuperar datos relevantes del sistema.

## 📁 Estructura del Proyecto

```plaintext
jamboree/
├── migrarJamboree/                   # Scripts y datos para pruebas de migración
├── odooDesarrollo/                   # Contenedor y módulos personalizados de Odoo
├── webAnexos/                        # Anexos web: cronograma, mapa, etc.
```

## ⚙️ Requisitos

- Docker
- Docker Compose
- Python 3

## 🛠️ Instalación y Puesta en Marcha

```bash
cd odooDesarrollo
docker-compose up --build
```

## 📄 Licencia

Este proyecto está distribuido bajo la licencia MIT.
