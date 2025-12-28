function loadMusicList() {
  fetch('/api/music')
    .then(res => res.json())
    .then(data => renderList(data))
    .catch(err => console.error('音楽リストの取得に失敗しました:', err));
}

function renderList(data) {
  const container = document.getElementById('music-list');
  container.innerHTML = '';
  data.forEach(music => {
    const item = document.createElement('div');
    item.className = 'mb-3 p-3 border rounded';

    item.innerHTML = `
      <h4>${music.title}</h4>
      <p><strong>アーティスト:</strong> ${music.artist}</p>
      <a href="${music.url}" target="_blank" class="btn btn-primary btn-sm">聴く</a>
    `;
    container.appendChild(item);
  });
}

window.onload = loadMusicList;