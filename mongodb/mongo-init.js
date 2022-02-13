db = db.getSiblingDB('db_aluno');

db.aluno.insertOne({
    _id: '942741',
    nome: 'Felipe Marques',
    turma: '42SCJ',
    _class: 'com.fmarxds.fiapspringcartaocredito.model.Aluno'
});


db.cartao.insertOne({
    _id: '0ee4d760-4370-4fdd-90f1-ec5e4a38fbc7',
    matricula_aluno: '942741',
    transacoes: [
        {
            estabelecimento: 'Pastelaria Carlinhos',
            valor: '50.59',
            tipo: 'CREDITO',
            parcelas: 0,
            data_hora: ISODate('2022-04-12T15:55:59.000Z')
        }
    ],
    _class: 'com.fmarxds.fiapspringcartaocredito.model.Cartao'
})