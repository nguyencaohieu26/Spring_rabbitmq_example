- Networking in Composer
  - Each container can now look up the hostname inside "services" and get back the
  appropriate container's IP address. For example: order-service' code, 
  payment-service' code and inventory-service' code could connect to the URL on
  right side of port: 
  <span style="color:#EF6D6D">9292</span> (external port) :<u style="color:#FFD365">8000</u>
  - It is important to note the distinction between <span>HOST_PORT</span> & <span>CONTAINER_PORT<span>.
  In my project, order-service the host name is 