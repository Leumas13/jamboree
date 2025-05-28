# Entorno de Desarrollo Odoo - Jamboree

Este directorio contiene el entorno de desarrollo de Odoo para el sistema Jamboree.

## Estructura

- `docker-compose.yml`: Orquestación de servicios Odoo, PostgreSQL y dependencias.
- `addons/`: Módulos personalizados.
- `DiagramaClases/`: Representación visual de la estructura de clases.
- `set_permissions.sh`: Script para la configuración de permisos en desarrollo.

## Puesta en Marcha

```bash
docker-compose up --build
```

Esto desplegará un entorno funcional de Odoo listo para pruebas de desarrollo con los módulos incluidos.

## Licencia

MIT
