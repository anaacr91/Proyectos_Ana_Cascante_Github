function Servicio() {
  const servicios = [
    {
      title: "Python Course",
      price: "20€",
      id: 1
    },
    {
      title: "JavaScript Course",
      price: "40€",
      id: 2
    }
  ]

  let boxes1 = []

  console.log(servicios)
  for (let i = 0; i < servicios.length; i++) {
    console.log(servicios[i])
    
    boxes1.push(
      <Servicio
        key={servicios[i].id}
        title={servicios[i].title}
        price={servicios[i].price}
        id={servicios[i].id}
      />
    )
  }

  return (
    <div className="equipo">{boxes1}</div>
  )
}

export default Servicio