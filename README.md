# SumTwitter

Para construção do aplicativo, foi utilizado o Android Studio 1.3, e o plugin Fabric para fazer a integração com o Twitter.
Foi adicionado dependência com o Google Service para utilização do Map.
O código está comentado, sendo auto-explicativo.
O design foi construído me baseando nas telas enviadas na Challenge - usando Activities.
Foram criadas 4 activities: 
  * MainActivity - tela inicial do sistema - para fazer o login
  * TimeLineActivity - tela que apresenta a timelime do twitter do usuário logado
  * MapActivity - tela que apresenta os twitts que possuem coordenadas geográficas publicadas

Foi necessário o registro no twitter para aquisição do ConsumerKey, da Secret na página do Twitter e da ApiKey do plugin Fabric; Da mesma forma, foi necessário o registro e geração de Chave para utilização dos mapas do GoogleMaps.
No mapa, ao acionar qualquer um dos twitts, o sistema mostra o usuáio que twitou e o twitt.
Foi criada uma classe chamada Localizacoes para guardar os Twitts que possuem coordenadas geográficas e criar as marcações dentro do Maps. Nessa classe, temos os atributos Id, Latitude, Longitude, Autor, Descrição, URLImagem e Bitmap; Ela foi criada para não precisarmos passar a lista completa de twitts para tratamento no Mapa - já passo a lista com os twitts com coordenadas e a Activity encarrega-se de criar as devidas marcações no mapa.
