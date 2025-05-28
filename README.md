# Ecosistema Jamboree

![Logo Jamboree](images/mi_imagen.png)

## 📦 Descripción General

El ecosistema está compuesto por los siguientes componentes principales:

- **Odoo personalizado**: módulo específico adaptado a las necesidades de Jamboree.
- **Entornos de desarrollo y producción**: configurados mediante Docker Compose y alojados en AWS.
- **Migración de datos**: herramientas y pruebas para importar información desde ficheros csv a Odoo.
- **Aplicación API externa**: conexión con Odoo mediante JSON-RPC2 para consulta de datos.
- **Aplicación móvil**: acceso a funcionalidades clave del cliente de Jamboree desde dispositivos móviles Android.

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

## 🚀 Tecnologías utilizadas
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=androidstudio,aws,bash,docker,github,kotlin,nginx,postgres,postman,py,ubuntuvscode," />
  </a>
</p>

---

## ⚙️ Requisitos Generales

- Docker
- Docker Compose
- Python 3 (para pruebas con notebooks y scripts)
- Entorno de desarrollo móvil (para `jamboreeMovil`)


---

## 📄 Licencia

Este proyecto está distribuido bajo la licencia MIT.
