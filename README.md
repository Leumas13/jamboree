
![Logo Jamboree](webAnexos/logo.png)

# Sistema empresarial para Jamboree
## 📦 Descripción General

El sistema está compuesto por los siguientes componentes principales:

- **Odoo personalizado**: módulo específico adaptado a las necesidades de Jamboree.
- **Entornos de desarrollo y producción**: configurados mediante Docker Compose y alojados en AWS.
- **Migración de datos**: herramientas y pruebas para importar información desde ficheros csv a Odoo.
- **Aplicación API externa**: conexión con Odoo mediante JSON-RPC2 para consulta de datos.
- **Aplicación móvil**: acceso a funcionalidades clave del cliente de Jamboree desde dispositivos móviles Android.

---

## 🚀 Tecnologías utilizadas
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=androidstudio,aws,bash,docker,github,kotlin,nginx,postgres,postman,py,ubuntuvscode," />
  </a>
</p>

---

## 📁 Estructura del Proyecto

jamboree/
├── index.html                      # Página de presentación de Anexos [web de anexos](https://leumas13.github.io/jamboree/)
├── migrarJamboree/                 # Scripts y datos para la migración de csv a Odoo
├── odooDesarrollo/                 # Entorno de desarrollo con módulos personalizados
├── odooProduccion/                 # Configuración para entorno de producción
├── jamboreeMovil/                  # Aplicación móvil conectada a Odoo
├── webAnexos/                      # Documentación adicional (mapa, Gantt, etc.)
└── README.md                       

